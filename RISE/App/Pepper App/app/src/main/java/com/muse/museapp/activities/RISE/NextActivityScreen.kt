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

class NextActivityScreen : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var mainMenuButton: Button

    private var duration = 0
    private var genre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.next_activity_screen)
        setButtons()
    }

    override fun onResume() {
        super.onResume()
        val extras: Bundle? = intent.extras
        if (extras != null) {
            duration = extras.getInt("duration")
            genre = extras.getString("genre").toString()
        }
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    fun setButtons() {

        backButton = findViewById<Button>(R.id.sing_finish_back)
        backButton.setOnClickListener {
            val intent = Intent(this, ChooseStressActivityScreen::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("duration", duration)
            intent.putExtra("genre", genre)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }

        mainMenuButton = findViewById<Button>(R.id.sing_finish_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        speak(qiContext)
    }

    fun speak(qiContext: QiContext) {
        val speech: Say = SayBuilder.with(qiContext)
            .withText("Great job!      " +
                    "Let's move on to the next activity.      ")
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