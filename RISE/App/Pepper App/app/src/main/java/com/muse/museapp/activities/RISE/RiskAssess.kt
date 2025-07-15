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

class RiskAssess : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var countryButton: Button
    private lateinit var rocknrollButton: Button
    private lateinit var backButton: Button
    private lateinit var repeatButton: Button
    private lateinit var mainMenuButton: Button

    private var duration = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.risk_assessment_intro)
        setButtons()
    }

    override fun onResume() {
        super.onResume()
        val extras: Bundle? = intent.extras
        if (extras != null) {
            duration = extras.getInt("duration")
        }
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    fun setButtons() {
        backButton = findViewById<Button>(R.id.choose_genre_back)
        backButton.setOnClickListener {
            val intent = Intent(this, ChooseModule::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }

        mainMenuButton = findViewById<Button>(R.id.choose_genre_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        repeatButton = findViewById<Button>(R.id.choose_genre_repeat)
        repeatButton.setOnClickListener {
            speak(qiContext)
        }

        speak(qiContext)
    }

    fun speak(qiContext: QiContext) {
        val speech: Say = SayBuilder.with(qiContext)
            .withText("Great! let's start with a simple Risk Assessment Form. Please answer these questions to let me know what kinds of issues you are having while caring for your loved one")
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
