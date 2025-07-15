package com.muse.museapp.activities.RISE

import android.animation.AnimatorSet
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.muse.museapp.R
import com.muse.museapp.activities.Welcome
import java.util.*


class StressOrKnowledge : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var pick_stress_management_exercise: Button
    private lateinit var pick_knowledge_module: Button
    private lateinit var mainMenuButton: Button

    private lateinit var mps: Array<MediaPlayer?>
    private lateinit var timer: Timer
    private lateinit var animatorSet: AnimatorSet

    private var duration = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.stress_or_knowledge)
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
        pick_stress_management_exercise = findViewById<Button>(R.id.pick_stress_management_exercise)
        pick_stress_management_exercise.setOnClickListener {
            val intent = Intent(this, ChooseStressActivityScreen::class.java)
            intent.putExtra("duration", duration)
            startActivity(intent)
        }

        pick_knowledge_module = findViewById<Button>(R.id.pick_knowledge_module)
        pick_knowledge_module.setOnClickListener {
            val intent = Intent(this, ChooseModule::class.java)
            intent.putExtra("duration", duration)
            startActivity(intent)
        }

        mainMenuButton = findViewById<Button>(R.id.rhythm_practice_mainmenu3)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    fun automaticTransition() {
        val intent = Intent(this, RiskAssess::class.java)
        intent.putExtra("duration", duration)
        startActivity(intent)
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        speak(qiContext)
    }

    fun speak(qiContext: QiContext) {
        val speech: Say = SayBuilder.with(qiContext)
            .withText("You have completed a module!       "+
                    "You can now choose to continue to another module or begin the stress management portion.       ")
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