package com.muse.museapp.activities.RISE

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.muse.museapp.R
import com.muse.museapp.activities.Welcome

class RiseQuizResults : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var repeatButton: Button
    private lateinit var mainMenuButton: Button
    private lateinit var correctAnswers: TextView

    private var duration = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.rise_quiz_results)
        setButtons()

        correctAnswers = findViewById<TextView>(R.id.textView45)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
        var correct = sharedPreference.getString("score",null)
        correctAnswers.setText("$correct/5 questions answered correctly")
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
        nextButton = findViewById<Button>(R.id.rhythm_transition3_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, StressOrKnowledge::class.java)
            intent.putExtra("duration", duration)
            startActivity(intent)
        }

        mainMenuButton = findViewById<Button>(R.id.rhythm_transition3_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        repeatButton = findViewById<Button>(R.id.rhythm_transition3_repeat)
        repeatButton.setOnClickListener {
            speak(qiContext)
        }

        speak(qiContext)
    }

    fun speak(qiContext: QiContext) {
        val speech: Say = SayBuilder.with(qiContext)
            .withText(
                "Nice work!      " +
                        "Check out how many answers you got right!      "
            )
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
