package com.muse.museapp.activities.GRA

import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.muse.museapp.R
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.muse.museapp.activities.Welcome

class ThankYouScreen : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var mainMenuButton: Button
    private var animatorSet: AnimatorSet? = null
    private lateinit var videoView : VideoView

    private var duration = 0
    private var genre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.thankyou_screen)
        setButtons()
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        animatorSet?.cancel()
    }

    fun setButtons() {
        mainMenuButton = findViewById<Button>(R.id.sing_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        speak(qiContext)
    }

//    private fun speak(qiContext: QiContext) {
//        val say = SayBuilder.with(qiContext)
//            .withText("Thank you for your time.             You have completed your genetic counseling screening and education.")
//            .build()
//        say.run()
//    }
    fun speak(qiContext: QiContext) {
        val response = "Thank you for your time.              You have completed your genetic education session. ";
        val phrase = Phrase("\\rspd=77\\ \\vct=72\\ $response");
        val speech: Say = SayBuilder.with(qiContext)
            .withPhrase(phrase)
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