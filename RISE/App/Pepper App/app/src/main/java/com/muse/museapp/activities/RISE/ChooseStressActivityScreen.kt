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

class ChooseStressActivityScreen : RobotActivity(), RobotLifecycleCallbacks {
//    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var repeatButton: Button
    private lateinit var mainMenuButton: Button
    private lateinit var BreathingButton: Button
    private lateinit var MeditatingButton: Button
    private lateinit var StretchingButton: Button
    private lateinit var GuidedImageryButton: Button

    private var duration = 0
    private var genre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.choose_stress_activity_screen)
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

    fun setButtons() {
        BreathingButton = findViewById<Button>(R.id.sing_transition1_breathing)
        BreathingButton.setOnClickListener {
            val intent = Intent(this, StressBreathingActivity::class.java)
            intent.putExtra("duration", duration)
            intent.putExtra("genre", genre)
            startActivity(intent)
        }
        MeditatingButton = findViewById<Button>(R.id.rhythm_transition1_meditating)
        MeditatingButton.setOnClickListener {
            val intent = Intent(this, StressMeditatingActivity::class.java)
            intent.putExtra("duration", duration)
            intent.putExtra("genre", genre)
            startActivity(intent)
        }
        StretchingButton = findViewById<Button>(R.id.stretching_button)
        StretchingButton.setOnClickListener {
            val intent = Intent(this, StressStretchingActivity::class.java)
            intent.putExtra("duration", duration)
            intent.putExtra("genre", genre)
            startActivity(intent)
        }
        GuidedImageryButton = findViewById<Button>(R.id.guided_imagery_button)
        GuidedImageryButton.setOnClickListener {
            val intent = Intent(this, StressGuidedImageryActivity::class.java)
            intent.putExtra("duration", duration)
            intent.putExtra("genre", genre)
            startActivity(intent)
        }

        backButton = findViewById<Button>(R.id.sing_transition1_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        mainMenuButton = findViewById<Button>(R.id.sing_transition1_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        repeatButton = findViewById<Button>(R.id.sing_transition1_repeat)
        repeatButton.setOnClickListener {
            speak(qiContext)
        }

        speak(qiContext)
    }

    fun speak(qiContext: QiContext) {
        val speech: Say = SayBuilder.with(qiContext)
            .withText("Choose which stress management activity you would like to do!")
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