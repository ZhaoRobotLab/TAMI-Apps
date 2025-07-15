package com.muse.museapp.activities.RISE

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.muse.museapp.R
import com.muse.museapp.activities.Welcome
import android.media.MediaPlayer


class StressGuidedImageryActivity : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var repeatButton: Button
    private lateinit var mainMenuButton: Button
    private var duration = 0
    private var genre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.stress_guided_imagery_activity)
        setButtons()
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        val extras: Bundle? = intent.extras
        if (extras != null) {
            duration = extras.getInt("duration")
            genre = extras.getString("genre").toString()
        }
    }
//    private fun playAudio() {
//        mediaPlayer = MediaPlayer.create(this, R.raw.breath) // Replace R.raw.your_audio_file with the resource ID of your MP3 file
//        mediaPlayer.start()
//    }


    fun setButtons() {
        nextButton = findViewById<Button>(R.id.sing_transition2_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, ThankYouScreen::class.java)
            intent.putExtra("duration", duration)
            startActivity(intent)
        }

        backButton = findViewById<Button>(R.id.sing_transition2_back)
        backButton.setOnClickListener {
            onBackPressed()
        }


        mainMenuButton = findViewById<Button>(R.id.sing_transition2_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        repeatButton = findViewById<Button>(R.id.sing_transition2_repeat)
        repeatButton.setOnClickListener {
            speak(qiContext)
        }

        speak(qiContext)
    }

    fun speak(qiContext: QiContext) {
        val speech: Say = SayBuilder.with(qiContext)
            .withText("Welcome to the Guided Imagery module.    In this module, we're going to explore the power of imagination.     Please find a comfortable seat and gently close your eyes.                     " +
                    "Close your eyes and begin deliberate breathing,        holding the breath for a few moments and then let it out slowly,        " +
                    "feeling relaxation as you do so.        And now take in another deep breath ...         " +
                    "hold it until you feel a little tension in your chest and then ...      let go ...      relax ...       " +
                    "allow all the muscles in your body to become loose, limp, soft like a rag doll,         just allow pleasant waves of relaxation to flow through you," +
                    "        soothing and relaxing each and every part of your body ...      including your arms and hands ...       " +
                    "your neck and shoulders ...         your scalp and all the muscles in your face ...         and as you rest there quietly,      " +
                    "breathing freely and evenly,        allow the muscles of your chest to become loose and relaxed ... and then your stomach and your back ... " +
                    "both your upper back and lower back ...        your hips and legs ...      allow the relaxation to flow through your legs all the way down your feet and ankles ...        " +
                    "and as I continue talking to you, these waves of relaxation can continue to spread throughout your body ...        " +
                    "penetrating deeply to every cell of your body ...      but no matter how relaxed you feel right now, it is possible to become even more deeply relaxed and yet awake and aware of my voice.        " +
                    "Now even though your attention may wander from time to time, simply bring it back to the images that I am going to describe.       " +
                    "Imagine yourself about to open a very large door, and as the door opens you suddenly find yourself transported to another place,   stepping out into a grassy meadow,  " +
                    "a peaceful, quiet meadow. And flowing through the meadow is a small winding stream, and on each side of the stream are tall shady trees ...        " +
                    "picture yourself right now sitting or lying down along the bank of this stream.        You may want to rest against the trunk of one of the trees ...      " +
                    "notice the pale blue sky and the fluffy white clouds,      feel the warmth of the sun with its pleasant rays shining down and sparkling as it reflects off the flowing water ...       " +
                    "it is a beautiful, pleasant, peaceful day, not too warm or too cold ...        " +
                    "the air is fresh and clean and you may even be aware of sounds of birds chirping or the sound of the water as it flows along the stream ...        " +
                    "it is so peaceful here ...         so calm and tranquil ...        just look around you ...        " +
                    "taking it all in ...       enjoy the simple beauty of this place.      And now look more closely at the stream ...        " +
                    "notice the clear, cool water as it flows by ...        perhaps wondering where the water comes from and where it goes ...      " +
                    "and as you look upstream you begin to notice what looks like a very large leaf floating on the water, " +
                    "and your eyes observe this leaf as it is getting closer and closer to the place where you are sitting ...      " +
                    "and then, when the leaf is just in front of you ...        " +
                    "you suddenly find yourself projecting and transferring to this leaf all of your concerns and discomfort. " +
                    "All of your cares and worries are transferred to the leaf, and so as the leaf continues to float down the stream, " +
                    "it carries away all of your discomfort, all of your cares and worries ...      " +
                    "just watch it float along, getting farther and farther away from you ...       " +
                    "until it finally disappears completely and you are left feeling even more relaxed, more comfortable, and more at ease than you have felt for a long time ...       " +
                    "For in this state of deep relaxation all parts of your body are working together harmoniously, smoothly, and healthily ... a deep sense of well-being fills your mind ...      " +
                    "a feeling of healthy energy and vitality fills your body ...       " +
                    "and as you prepare to eventually leave this special place of relaxation, you can carry back with you many of these pleasant feelings and sensations, " +
                    "knowing that as you practice this exercise and similar exercises, it will become easier to use the powers of your mind to experience these positive effects ...        " +
                    "And now I will bring you back slowly from this relaxation by counting backwards from 3 to 1. " +
                    "When I get to 1, you’ll be alert, refreshed, and comfortable. Okay, “3” much more alert; “2” feeling refreshed and comfortable,")
            .build()
        speech.run()
    }

    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
    }

    override fun onRobotFocusRefused(reason: String?) {
        TODO("Not yet implemented")
    }
}