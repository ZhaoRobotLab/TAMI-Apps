package com.muse.museapp.activities.RISE

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.muse.museapp.R
import com.muse.museapp.activities.Welcome
import android.media.MediaPlayer

//may 18 ver

class StressStretchingActivity : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var repeatButton: Button
    private lateinit var mainMenuButton: Button
    private var duration = 0
    private var genre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.stress_stretching_activity)
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
        playDanceAnimation(qiContext, R.raw.stretching_01)
    }

    fun speak(qiContext: QiContext) {
        val speech: Say = SayBuilder.with(qiContext)
            .withText("Welcome to the Stretching module.     In this module, we will do a simple stretching.      Please stand up and make sure you have enough space around you.      Please follow along with my movements.")
            .build()
        speech.run()
    }


    fun playDanceAnimation(qiContext: QiContext, animResource: Int) {
        val animation = AnimationBuilder.with(qiContext)
            .withResources(animResource)
            .build()
        val animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation)
            .build()
            .async()
        animate.run()
    }

    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
    }

    override fun onRobotFocusRefused(reason: String?) {
        TODO("Not yet implemented")
    }
}