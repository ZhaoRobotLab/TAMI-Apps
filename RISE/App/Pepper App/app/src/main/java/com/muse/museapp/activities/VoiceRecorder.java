package com.muse.museapp.activities;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Continuously records audio and notifies the {@link Callback} when voice (or any
 * sound) is heard.
 *
 * <p>The recorded audio format is always {@link AudioFormat#ENCODING_PCM_16BIT} and
 * {@link AudioFormat#CHANNEL_IN_MONO}. This class will automatically pick the right sample rate
 * for the device. Use {@link #getSampleRate()} to get the selected value.</p>
 */
public class VoiceRecorder {

    private static final int[] SAMPLE_RATE_CANDIDATES = new int[]{16000, 11025, 22050, 44100};

    private static final int CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    private static final int AMPLITUDE_THRESHOLD = 1500;
    private static final int SPEECH_TIMEOUT_MILLIS = 2000;
    private static final int MAX_SPEECH_LENGTH_MILLIS = 30 * 1000;

    public static abstract class Callback {

        /**
         * Called when the recorder starts hearing voice.
         */
        public void onVoiceStart() {
        }

        /**
         * Called when the recorder is hearing voice.
         *
         * @param data The audio data in {@link AudioFormat#ENCODING_PCM_16BIT}.
         * @param size The size of the actual data in {@code data}.
         */
        public void onVoice(byte[] data, int size) {
        }

        /**
         * Called when the recorder stops hearing voice.
         */
        public void onVoiceEnd() {
        }
    }


    private static final String TAG = "VoiceRecorder";

    private final Callback mCallback;

    private AudioRecord mAudioRecord;

    public Thread mThread;

    public byte[] mBuffer;

    public boolean flag_next = false;

    private final Object mLock = new Object();

    /** The timestamp of the last time that voice is heard. */
    private long mLastVoiceHeardMillis = Long.MAX_VALUE;

    /** The timestamp when the current voice is started. */
    private long mVoiceStartedMillis;

    public VoiceRecorder(@NonNull Callback callback) {
        mCallback = callback;
    }

    /**
     * Starts recording audio.
     *
     * <p>The caller is responsible for calling {@link #stop()} later.</p>
     */
    public void start() {
        // Stop recording if it is currently ongoing.
        stop();
        // Try to create a new recording session.
        mAudioRecord = createAudioRecord();
        if (mAudioRecord == null) {
            throw new RuntimeException("Cannot instantiate VoiceRecorder");
        }
        // Start recording.
        mAudioRecord.startRecording();
        // Start processing the captured audio.
        mThread = new Thread(new ProcessVoice());
        mThread.start();

//        Log.i(TAG, "--Success");
    }

    /**
     * Stops recording audio.
     */
    public void stop() {
        synchronized (mLock) {
            dismiss();
            if (mThread != null) {
                mThread.interrupt();
                mThread = null;
            }
            if (mAudioRecord != null) {
                Log.i(TAG, "releasing voice recorder");
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            }
            mBuffer = null;
        }

//        Log.i(TAG, "-- Success");
    }

    /**
     * Dismisses the currently ongoing utterance.
     */
    public void dismiss() {
        if (mLastVoiceHeardMillis != Long.MAX_VALUE) {
            mLastVoiceHeardMillis = Long.MAX_VALUE;
            mCallback.onVoiceEnd();
        }
    }

    public void overrideVoiceRecorder(){
        synchronized (mLock) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            if (mBuffer != null) {
                final int size = mAudioRecord.read(mBuffer, 0, mBuffer.length);
                mCallback.onVoice(mBuffer, size);
            }
        }
    }

    /**
     * Retrieves the sample rate currently used to record audio.
     *
     * @return The sample rate of recorded audio.
     */
    public int getSampleRate() {
        if (mAudioRecord != null) {
            return mAudioRecord.getSampleRate();
        }
        return 0;
    }

    /**
     * Creates a new {@link AudioRecord}.
     *
     * @return A newly created {@link AudioRecord}, or null if it cannot be created (missing
     * permissions?).
     */
    private AudioRecord createAudioRecord() {
        for (int sampleRate : SAMPLE_RATE_CANDIDATES) {
            final int sizeInBytes = AudioRecord.getMinBufferSize(sampleRate, CHANNEL, ENCODING);
            if (sizeInBytes == AudioRecord.ERROR_BAD_VALUE) {
                continue;
            }
            final AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    sampleRate, CHANNEL, ENCODING, sizeInBytes);
            if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                mBuffer = new byte[sizeInBytes];
                return audioRecord;
            } else {
                audioRecord.release();
            }
        }
        return null;
    }

    /**
     * Continuously processes the captured audio and notifies {@link #mCallback} of corresponding
     * events.
     */
    public class ProcessVoice implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (mLock) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    if(mBuffer!=null){
                        final int size = mAudioRecord.read(mBuffer, 0, mBuffer.length);
                        final long now = System.currentTimeMillis();
                        if (isHearingVoice(mBuffer, size)) {
                            if (mLastVoiceHeardMillis == Long.MAX_VALUE) {
                                mVoiceStartedMillis = now;
                                mCallback.onVoiceStart();
                            }
                            mCallback.onVoice(mBuffer, size);
                            mLastVoiceHeardMillis = now;
                            if (now - mVoiceStartedMillis > MAX_SPEECH_LENGTH_MILLIS) {
                                end();
                            }
                        } else if (mLastVoiceHeardMillis != Long.MAX_VALUE) {
                            mCallback.onVoice(mBuffer, size);
                            if (now - mLastVoiceHeardMillis > SPEECH_TIMEOUT_MILLIS || flag_next) {
                                //Log.d("VoiceRecorder", "calling end()");
                                end();
                            }
                        }
                    }
                }
            }
        }

        private void end() {
            mLastVoiceHeardMillis = Long.MAX_VALUE;
            mCallback.onVoiceEnd();
        }

        private boolean isHearingVoice(byte[] buffer, int size) {
            for (int i = 0; i < size - 1; i += 2) {
                // The buffer has LINEAR16 in little endian.
                int s = buffer[i + 1];
                if (s < 0) s *= -1;
                s <<= 8;
                s += Math.abs(buffer[i]);
                if (s > AMPLITUDE_THRESHOLD) {
                    return true;
                }
            }
            return false;
        }

        //this is used to override the natural flow of the voice recorder.
        //this can be used when dev wants to end speech early and not wait on silence
        public void overrideVoiceRecorder(){
            if(mBuffer!=null) {
                final int size = mAudioRecord.read(mBuffer, 0, mBuffer.length);
                final long now = System.currentTimeMillis();

                if (isHearingVoice(mBuffer, size)) {
                    if (mLastVoiceHeardMillis == Long.MAX_VALUE) {
                        mVoiceStartedMillis = now;
                        mCallback.onVoiceStart();
                    }
                    mCallback.onVoice(mBuffer, size);

                    end();
                } else if (mLastVoiceHeardMillis != Long.MAX_VALUE) {
                    mCallback.onVoice(mBuffer, size);
                    end();
                }
            }
        }

    }

}
