package com.muse.museapp.activities.GRA

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.muse.museapp.R
import com.muse.museapp.activities.Welcome

class GraHome: RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var mainMenuButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.gra_home)
        setButtons()
    }

    fun setButtons() {
        nextButton = findViewById(R.id.home_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, EnterName::class.java)
            startActivity(intent)
        }

        backButton = findViewById(R.id.home_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        mainMenuButton = findViewById(R.id.home_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        speak(qiContext)
    }

    fun speak(qiContext: QiContext) {
        val response = "Hello! Welcome to your genetic education session. " +
                "My name is Tammy, and I will be your guide. " +
                "You can navigate this app using the buttons. " +
                "Press the back button to return to the previous screen. " +
                "And press the next button when you are ready to continue to the next screen.";

        val phrase = Phrase ("\\rspd=77\\ \\vct=72\\ $response");
        val speech: Say = SayBuilder.with(qiContext)
            .withPhrase(phrase)
            .build()
        speech.run()
    }

    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
    }

    override fun onRobotFocusRefused(reason: String) {
        TODO("Not yet implemented")
    }
}