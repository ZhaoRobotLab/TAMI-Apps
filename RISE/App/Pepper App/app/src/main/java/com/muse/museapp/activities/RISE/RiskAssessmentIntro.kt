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

class RiskAssessmentIntro : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var repeatButton: Button
    private lateinit var mainMenuButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.risk_assesing)
        setButtons()
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    fun setButtons() {
        nextButton = findViewById<Button>(R.id.seat_reminder1_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, RiskAssessmentQuestions::class.java)
            startActivity(intent)
        }

        backButton = findViewById<Button>(R.id.seat_reminder1_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        mainMenuButton = findViewById<Button>(R.id.seat_reminder1_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        repeatButton = findViewById<Button>(R.id.seat_reminder1_repeat)
        repeatButton.setOnClickListener {
            speak(qiContext)
        }

        speak(qiContext)
    }

    fun speak(qiContext: QiContext) {
        val speech: Say = SayBuilder.with(qiContext)
            .withText("Before we begin, please take a quick risk assessment questionnaire!    ")
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