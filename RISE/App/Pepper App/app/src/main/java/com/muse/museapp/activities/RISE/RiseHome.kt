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

class RiseHome: RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var mainMenuButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.rise_home)
        setButtons()
    }

    fun setButtons() {
        nextButton = findViewById<Button>(R.id.home_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, RiskAssessmentIntro::class.java)
            startActivity(intent)
        }

        backButton = findViewById<Button>(R.id.home_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        mainMenuButton = findViewById<Button>(R.id.home_mainmenu)
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
        val speech: Say = SayBuilder.with(qiContext)
            .withText("Hello! Welcome to the rise, the Robot-based Information and Support to Enhance Alzheimer’s Caregiver Health.      " +
                    "My name is Tammy, and I will be your guidance through this app         " +
                    "You can navigate this app using the buttons.      " +
                    "Press the back button to return to the previous screen.      " +
                    "and press the next button when you are ready to continue to the next screen.      ")
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