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

class GoodbyeScreen: RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var backButton: Button
    private lateinit var repeatButton: Button
    private lateinit var mainMenuButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.goodbye_screen)
        setButtons()
    }

    fun setButtons() {
        backButton = findViewById<Button>(R.id.goodbye_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        mainMenuButton = findViewById<Button>(R.id.goodbye_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        repeatButton = findViewById<Button>(R.id.goodbye_repeat)
        repeatButton.setOnClickListener {
            speak(qiContext)
        }

        speak(qiContext)
    }

    fun speak(qiContext: QiContext) {
        val speech: Say = SayBuilder.with(qiContext)
            .withText("Thank you for participating!      " +
                    "I hope to see you again soon!      " +
                    "Press 'main menu' to return to the start screen.      ")
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