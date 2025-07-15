package com.muse.museapp.utils

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.chaquo.python.Python
import com.muse.museapp.activities.SpeechService
import com.muse.museapp.activities.TutorialActivity
import com.muse.museapp.activities.VoiceRecorder
import com.muse.museapp.ui.conversation.ConversationItemType
import com.muse.museapp.ui.conversation.ConversationView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldebaran.qi.Future
import com.aldebaran.qi.QiException
import com.aldebaran.qi.sdk.`object`.conversation.*
//import com.tammy.tammyapp.activities.MessageClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @param qiContext
 * @param initConvHistory
 * @param preDefQuestions
 * @param introStr
 * @param outroStr
 */

class GoogleTTS(qiContext: QiContext, initConvHistory: String, preDefQuestions: Array<String>,
                introStr: String, outroStr: String, conversationView: ConversationView, callingActivity: TutorialActivity
): ViewModel() {
    companion object{
        var instance: GoogleTTS? = null
    }

    var mSpeechService: SpeechService? = null
    var mVoiceRecorder: VoiceRecorder? = null
    private var recordedSpeech: String? = null      // buffer storing recorded speech (reset in nextButton())
    val TAG = "GoogleTTS"

    //openai
    var original: String = ""
    var myString: String = ""
    var genResponse: Array<String> = arrayOf("Wow! That is very interesting.", "Oh, I see.",
                                             "I feel the same way.","That's good.", "That's very nice.")

    // activity
    var convHist: String = initConvHistory
    var questions: Array<String> = preDefQuestions
    var activity = callingActivity
    var inStr = introStr
    var outStr = outroStr
    var qiContext = qiContext

    //store makesay future
    private var makeSayFuture: Future<Void>? = null

    // control
    public var counter: Int = 0
    public var recordingFlag: Boolean? = null
    public var startFlag = false
    public var nextFlag = false
    public var vidFlag = false
    public var finFlag = false

    init {
        // guarantee that the microphone is off at the beginning of the activity
        stopVoiceRecorder(TAG,"init")

        // guarantee that the conversation is cleared
        //activity.clearConvo()

        // bind the service to the calling activity (untested)
        //bindToActivity(callingActivity)

        // say intro statement
        Log.d(TAG, "initializing conversation with -> $introStr")
        viewModelScope.launch(Dispatchers.Default) {
            makeSay(introStr)
            vidFlag = true // enables videos for activities using videos
        }
        convHist += "Tammy: $introStr\n" // add this to the conversation history
        counter = 0
    }

    private fun makeSay(text: String, qFlag: Boolean = false){
        stopVoiceRecorder(TAG,"makeSay")
        val say = SayBuilder.with(qiContext).withText(text).build()

        if(makeSayFuture != null){
            makeSayFuture?.andThenConsume{
                runSay(say, qFlag, text)
            }
        } else {
            runSay(say, qFlag, text)
        }
    }

    /**
     * @param qFlag used to conditionally start the voice recorder after asking a question
     */
    private fun runSay(say: Say, qFlag: Boolean = false, text: String) {
        try {
            makeSayFuture = say.async().run()
            makeSayFuture?.andThenConsume {
                if (qFlag) {
                    startVoiceRecorder(TAG, "runSay")
                }
            }
        } catch(e: QiException){
            Log.e("makeSay", e.toString())
        }
        Log.i("$TAG/runSay", "finished say call for {$text}")
    }

    /*
    fun startConvo() {
        viewModelScope.launch(Dispatchers.Default) {
            makeSay(questions[counter])
        }
        convHist += "Tammy: ${questions[counter]}" // add this to the conversation history
        recordingFlag = true
        counter++
        startVoiceRecorder()
    }

     */

    fun restartConvo() {
        // reset variables
        activity.clearConvo()
        startFlag = false
        convHist = ""

        // stop voice recorder functions
        stopVoiceRecorder(TAG,"restartConvo")

        // restart conversation
        Log.d(TAG, "restarting conversation with -> $inStr")
        viewModelScope.launch(Dispatchers.IO) {
            makeSay(inStr)
        }
        convHist += "Tammy: $inStr\n" // add this to the conversation history
    }

    fun repeatButton() {
        if (counter >= questions.size) {
            return
        }

        // stop voice recorder
        stopVoiceRecorder(TAG,"repeatButton")

        // repeat previous question
        if (counter != 0) {
            counter--
        }
        askQuestion()
    }

    fun nextButton() {
        if (counter == 0) { // ask first question (no questions have been asked)
            viewModelScope.launch(Dispatchers.Default) {
                makeSay(questions[0], true)
            }
            convHist += "Tammy: ${questions[0]}" // add this to the conversation history
            recordingFlag = true
            counter = 1
        } else if (counter <= questions.size) { // skip question (haven't asked all questions)
            //**** The voice recorder NEEDS to not be on at this point
            stopVoiceRecorderNextButton(TAG, "nextButton")
            recordingFlag = false
            responseHandler()
        } else {
            finFlag = true
            Log.w("$TAG/nextButton", "NEXT PRESSED AT THE END")
        }
    }

    /**
     * handle a person's response (which is stored in the speech buffer)
     * Take the speech buffer and send it to openai to generate a comment; ask the next question
     */
    private fun responseHandler() { // previously called onNext
        // format input before passing it to openai
        val myString = addNextLine(convHist, recordedSpeech, "")
        Log.d("$TAG/responseHandler", "input to openai: {$recordedSpeech}")
        Log.d("$TAG/responseHandler", "full input to openai: {$myString}")

        // use openai to generate a response (it's response is stored in "answer")
        var answer = testResponse(myString)
        Log.d("$TAG/responseHandler", "openai response: {$answer}")

        // handle any questions generated by openAI
        answer = removeQuestion(answer, convHist, genResponse)
        Log.d("$TAG/responseHandler", "after removing questions: {$answer}")

        // update conversation history
        convHist = addNextLine(myString, "", answer)

        // reset recordedSpeech
        recordedSpeech = ""

        // progress the conversation
        askQuestion(answer)
        Log.i("$TAG/responseHandler","finished onNext")
    }

    fun onSkip() {
        // guarantee that the counter doesn't index out-of-bounds
        if (counter >= questions.size) {
            Log.w("$TAG/onSkip", "PREVENTING SKIP")
            return
        }

        // stop voice recorder
        stopVoiceRecorder(TAG,"onSkip")

        // skip to next question
        askQuestion()
    }

    private fun askQuestion(answer: String = "") {
        // ask question
        Log.i("$TAG/askQuestion", "attempting to ask the next question")
        viewModelScope.launch(Dispatchers.Default) {
            if (counter == questions.size) { // outro statement
                makeSay("$answer $outStr")
                //activity.addConvoLine(outStr, ConversationItemType.ROBOT_OUTPUT)
                convHist += "\nTammy: $outStr\n" // add this to the conversation history
                counter += 1
                finFlag = true
                //closeSpeech() // Not sure what this will do
            } else if (counter <= questions.size){ // general questions
                makeSay("$answer ${questions[counter]}", true)
                //activity.addConvoLine(questions[counter], ConversationItemType.ROBOT_OUTPUT)
                convHist += "\nTammy: ${questions[counter]}" // add this to the conversation history
                counter += 1
            }
        }
    }

    fun closeSpeech(){
        Log.i(TAG, "finalizing GoogleTTS")
        stopVoiceRecorder(TAG,"closeSpeech")
        activity.unbindService(mServiceConnection)
    }

    val mVoiceCallback: VoiceRecorder.Callback = object : VoiceRecorder.Callback() {
        override fun onVoiceStart() {
//            if (mSpeechService == null)     Log.i(TAG, "mSpeechService is NULL");
//            if (mVoiceRecorder == null)     Log.i(TAG, "mVoiceRecorder is NULL");
            if (mSpeechService != null && mVoiceRecorder != null) mSpeechService!!.setConfig(
                mVoiceRecorder!!.sampleRate,
                "en-US"
            )
        }

        override fun onVoice(data: ByteArray?, size: Int) {
            if (mSpeechService != null) {
                Log.i("onVoice", "$data $size")
                mSpeechService!!.recognize(data, size)
            }
        }

        override fun onVoiceEnd() {
            if (mSpeechService != null) mSpeechService!!.finishRecognizing()
        }
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, binder: IBinder) {
            Log.i(TAG, "in service connection")
            mSpeechService = SpeechService.from(binder)
            mSpeechService!!.addListener(mSpeechServiceListener)
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mSpeechService = null
        }
    }

    /**
     * Listens for an output to the service. When a sentence is recognized, identify the language of it, and starts the chatData.
     * Done if isFinal is true, meaning if the recognizing process is done.
     */
    private val mSpeechServiceListener: SpeechService.Listener = object : SpeechService.Listener {
        // define the function that will be used by the SpeechService
        override fun onSpeechRecognized(text: String?, isFinal: Boolean) {
            //stop the recorder while checking if we need to move to the next
            if (isFinal){
                //stopVoiceRecorder(TAG, "onSpeechRecognized")
            }

            //put what the user said onto the screen
            Log.i("OnSpeechRecognized", text!!)
            activity.addConvoLine(text, ConversationItemType.HUMAN_INPUT)

            // add recognized speech to the buffer
            if (recordedSpeech == null) {
                recordedSpeech = text
            } else {
                recordedSpeech += ". $text"
            }
        }
    }

    // {-- functions needed for STT
    private fun startVoiceRecorder(tag: String = ".", function: String = ".") {
        Log.i("$tag/$function", "Started Voice Recorder -- ")

        //start recorder
        if (mVoiceRecorder != null) mVoiceRecorder!!.stop()
        mVoiceRecorder = VoiceRecorder(mVoiceCallback)
        mVoiceRecorder!!.start()
    }

    fun stopVoiceRecorder(tag: String = ".", function: String = ".") {
        Log.i("$tag/$function", "Stopped Voice Recorder -- ")

        if (mVoiceRecorder != null) {
            mVoiceRecorder!!.stop()
            mVoiceRecorder = null
        }
    }

    fun stopVoiceRecorderNextButton(tag: String = ".", function: String = "."){
        Log.i("$tag/$function", "Stopped Voice Recorder -- ")

        if (mVoiceRecorder != null) {
            mVoiceRecorder!!.overrideVoiceRecorder()
            mVoiceRecorder!!.stop()
            mVoiceRecorder = null
        }
    }


    /**
     * add a line to the conversation history
     *
     * @param original the original conversation history
     * @param prompt
     * @return the updated conversation history.
     */
    fun fixInput(original: String?, prompt: String?, ans: String?): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("myScript")
        return pythonFile.callAttr("addNextLine", original, prompt, ans).toString()
    }

    /**
     * takes an input and gets a response from Openai
     * @param prompt the prompt sent to openAI
     * @return openAI's response to the prompt
     */
    fun getString(prompt: String?): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("myScript")
        return pythonFile.callAttr("testResponse", prompt).toString()
    }

    // formats input
    fun addNextLine(original: String?, prompt: String?, ans: String?): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("myScript")
        return pythonFile.callAttr("addNextLine", original, prompt, ans).toString()
    }

    /**
     * remove any questions that were generated by openAI from its response
     * @param original the original response
     * @param convHistory the conversation history -- used for default statements
     * @return the formatted response
     */
    fun removeQuestion(original: String?, convHistory: String?, genResponse: Array<String>): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("myScript")
        return pythonFile.callAttr("removeQuestion", original, convHistory, genResponse).toString()
    }

    // takes an input and gets a response from Openai
    fun testResponse(prompt: String?): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("myScript")
        return pythonFile.callAttr("testResponse", prompt).toString()
    }

    fun bindToActivity(bindActivity: Context){
        val activityName = bindActivity.toString()
        Log.i("TTS", "binding to $activityName")
        bindActivity.bindService(Intent(bindActivity, SpeechService::class.java), mServiceConnection, BIND_AUTO_CREATE)
    }

}
