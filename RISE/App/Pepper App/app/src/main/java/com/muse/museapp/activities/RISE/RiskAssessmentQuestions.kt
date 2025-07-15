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
import androidx.core.content.ContextCompat

class RiskAssessmentQuestions : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var backButton: Button
    private lateinit var repeatButton: Button
    private lateinit var mainMenuButton: Button
    private lateinit var nextButton: Button
    private lateinit var question1: Button
    private lateinit var question2: Button
    private lateinit var question3: Button
    private lateinit var question4: Button
    private lateinit var question5: Button
    private lateinit var question6: Button
    private lateinit var question7: Button
    private lateinit var question8: Button
    private lateinit var question9: Button
    private lateinit var question10: Button
    private lateinit var question11: Button
    private lateinit var question12: Button
    private lateinit var question13: Button
    private lateinit var question14: Button
    private lateinit var question15: Button
    private lateinit var question16: Button
    private lateinit var question17: Button
    private lateinit var question18: Button
    private lateinit var question19: Button
    private lateinit var question20: Button
    private lateinit var question21: Button
    private lateinit var question22: Button
    private lateinit var question23: Button

    // Create a Vector to store the module reccomendations
    private var moduleReccomendations: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.risk_assesment_questions)
        setButtons()
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    fun setButtons() {
        var duration: Int = 0

        backButton = findViewById<Button>(R.id.pick_duration_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        mainMenuButton = findViewById<Button>(R.id.pick_duration_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, ChooseModule::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }

        nextButton = findViewById<Button>(R.id.pick_duration_next)
        nextButton.setOnClickListener {
            // Save the module reccomendations to shared preferences
            val sharedPref = getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.apply {
                putStringSet("moduleReccomendations", moduleReccomendations.toSet())
                apply()
            }
            val intent = Intent(this, ChooseModule::class.java)
            intent.putExtra("duration", duration)
            startActivity(intent)
        }

        question1 = findViewById<Button>(R.id.yes_button_1)
        question1.setOnClickListener {
            if (!moduleReccomendations.contains("Sundowning")) {
                moduleReccomendations.add("Sundowning")
                question1.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            }
            else{
                moduleReccomendations.remove("Sundowning")
                question1.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }
        question2 = findViewById<Button>(R.id.yes_button_2)
        question2.setOnClickListener {
            if (!moduleReccomendations.contains("Activities")) {
                moduleReccomendations.add("Activities")
                question2.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Activities")
                question2.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question3 = findViewById<Button>(R.id.yes_button_3)
        question3.setOnClickListener {
            if (!moduleReccomendations.contains("Bathing")) {
                moduleReccomendations.add("Bathing")
                question3.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Bathing")
                question3.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question4 = findViewById<Button>(R.id.yes_button_4)
        question4.setOnClickListener {
            if (!moduleReccomendations.contains("Combativeness")) {
                moduleReccomendations.add("Combativeness")
                question4.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Combativeness")
                question4.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question5 = findViewById<Button>(R.id.yes_button_5)
        question5.setOnClickListener {
            if (!moduleReccomendations.contains("Confusion")) {
                moduleReccomendations.add("Confusion")
                question5.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Confusion")
                question5.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question6 = findViewById<Button>(R.id.yes_button_6)
        question6.setOnClickListener {
            if (!moduleReccomendations.contains("Confusion")) {
                moduleReccomendations.add("Confusion")
                question6.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Confusion")
                question6.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question7 = findViewById<Button>(R.id.yes_button_7)
        question7.setOnClickListener {
            if (!moduleReccomendations.contains("Confusion")) {
                moduleReccomendations.add("Confusion")
                question7.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Confusion")
                question7.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question8 = findViewById<Button>(R.id.yes_button_8)
        question8.setOnClickListener {
            if (!moduleReccomendations.contains("Depression")) {
                moduleReccomendations.add("Depression")
                question8.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Depression")
                question8.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question9 = findViewById<Button>(R.id.yes_button_9)
        question9.setOnClickListener {
            if (!moduleReccomendations.contains("Depression")) {
                moduleReccomendations.add("Depression")
                question9.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Depression")
                question9.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question10 = findViewById<Button>(R.id.yes_button_10)
        question10.setOnClickListener {
            if (!moduleReccomendations.contains("Dressing")) {
                moduleReccomendations.add("Dressing")
                question10.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Dressing")
                question10.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question11 = findViewById<Button>(R.id.yes_button_11)
        question11.setOnClickListener {
            if (!moduleReccomendations.contains("Eating")) {
                moduleReccomendations.add("Eating")
                question11.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Eating")
                question11.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question12 = findViewById<Button>(R.id.yes_button_12)
        question12.setOnClickListener {
            if (!moduleReccomendations.contains("Hallucinations & Delusions")) {
                moduleReccomendations.add("Hallucinations & Delusions")
                question12.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Hallucinations & Delusions")
                question12.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question13 = findViewById<Button>(R.id.yes_button_13)
        question13.setOnClickListener {
            if (!moduleReccomendations.contains("Hallucinations & Delusions")) {
                moduleReccomendations.add("Hallucinations & Delusions")
                question13.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Hallucinations & Delusions")
                question13.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question14 = findViewById<Button>(R.id.yes_button_14)
        question14.setOnClickListener {
            if (!moduleReccomendations.contains("Incontinence")) {
                moduleReccomendations.add("Incontinence")
                question14.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Incontinence")
                question14.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question15 = findViewById<Button>(R.id.yes_button_15)
        question15.setOnClickListener {
            if (!moduleReccomendations.contains("Sleeping")) {
                moduleReccomendations.add("Sleeping")
                question15.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Sleeping")
                question15.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question16 = findViewById<Button>(R.id.yes_button_16)
        question16.setOnClickListener {
            if (!moduleReccomendations.contains("Repeated Questions")) {
                moduleReccomendations.add("Repeated Questions")
                question16.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Repeated Questions")
                question16.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question17 = findViewById<Button>(R.id.yes_button_17)
        question17.setOnClickListener {
            if (!moduleReccomendations.contains("Sexuality")) {
                moduleReccomendations.add("Sexuality")
                question17.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Sexuality")
                question17.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question18 = findViewById<Button>(R.id.yes_button_18)
        question18.setOnClickListener {
            if (!moduleReccomendations.contains("Shadowing")) {
                moduleReccomendations.add("Shadowing")
                question18.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Shadowing")
                question18.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question19 = findViewById<Button>(R.id.yes_button_19)
        question19.setOnClickListener {
            if (!moduleReccomendations.contains("Visiting")) {
                moduleReccomendations.add("Visiting")
                question19.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Visiting")
                question19.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question20 = findViewById<Button>(R.id.yes_button_20)
        question20.setOnClickListener {
            if (!moduleReccomendations.contains("Wandering")) {
                moduleReccomendations.add("Wandering")
                question20.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Wandering")
                question20.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question21 = findViewById<Button>(R.id.yes_button_21)
        question21.setOnClickListener {
            if (!moduleReccomendations.contains("Driving")) {
                moduleReccomendations.add("Driving")
                question21.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Driving")
                question21.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question22 = findViewById<Button>(R.id.yes_button_22)
        question22.setOnClickListener {
            if (!moduleReccomendations.contains("Safety")) {
                moduleReccomendations.add("Safety")
                question22.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Safety")
                question22.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }

        question23 = findViewById<Button>(R.id.yes_button_23)
        question23.setOnClickListener {
            if (!moduleReccomendations.contains("Dental Care")) {
                moduleReccomendations.add("Dental Care")
                question23.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
            } else {
                moduleReccomendations.remove("Dental Care")
                question23.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultRisk)) // Set color back to blue
            }
        }
//
//        question2 = findViewById<Button>(R.id.yes_button_2)
//        question2.setOnClickListener {
//            if (!moduleReccomendations.contains("Activities")) {
//                moduleReccomendations.add("Activities")
//            }
//            else{
//                moduleReccomendations.remove("Activities")
//            }
//        }
//
//        question3 = findViewById<Button>(R.id.yes_button_3)
//        question3.setOnClickListener {
//            if (!moduleReccomendations.contains("Bathing")) {
//                moduleReccomendations.add("Bathing")
//            }
//            else{
//                moduleReccomendations.remove("Bathing")
//            }
//        }
//
//        question4 = findViewById<Button>(R.id.yes_button_4)
//        question4.setOnClickListener {
//            if (!moduleReccomendations.contains("Combativeness")) {
//                moduleReccomendations.add("Combativeness")
//            }
//            else{
//                moduleReccomendations.remove("Combativeness")
//            }
//        }
//
//        question5 = findViewById<Button>(R.id.yes_button_5)
//        question5.setOnClickListener {
//            if (!moduleReccomendations.contains("Confusion")) {
//                moduleReccomendations.add("Confusion")
//            }
//            else{
//                moduleReccomendations.remove("Confusion")
//            }
//        }
//
//        question6 = findViewById<Button>(R.id.yes_button_6)
//        question6.setOnClickListener {
//            if (!moduleReccomendations.contains("Confusion")) {
//                moduleReccomendations.add("Confusion")
//            }
//            else{
//                moduleReccomendations.remove("Confusion")
//            }
//        }
//
//        question7 = findViewById<Button>(R.id.yes_button_7)
//        question7.setOnClickListener {
//            if (!moduleReccomendations.contains("Confusion")) {
//                moduleReccomendations.add("Confusion")
//            }
//            else{
//                moduleReccomendations.remove("Confusion")
//            }
//        }
//
//        question8 = findViewById<Button>(R.id.yes_button_8)
//        question8.setOnClickListener {
//            if (!moduleReccomendations.contains("Depression")) {
//                moduleReccomendations.add("Depression")
//            }
//            else{
//                moduleReccomendations.remove("Depression")
//            }
//        }
//
//        question9 = findViewById<Button>(R.id.yes_button_9)
//        question9.setOnClickListener {
//            if (!moduleReccomendations.contains("Depression")) {
//                moduleReccomendations.add("Depression")
//            }
//            else{
//                moduleReccomendations.remove("Depression")
//            }
//        }
//
//        question10 = findViewById<Button>(R.id.yes_button_10)
//        question10.setOnClickListener {
//            if (!moduleReccomendations.contains("Dressing")) {
//                moduleReccomendations.add("Dressing")
//            }
//            else{
//                moduleReccomendations.remove("Dressing")
//            }
//        }
//
//        question11 = findViewById<Button>(R.id.yes_button_11)
//        question11.setOnClickListener {
//            if (!moduleReccomendations.contains("Eating")) {
//                moduleReccomendations.add("Eating")
//            }
//            else{
//                moduleReccomendations.remove("Eating")
//            }
//        }
//
//        question12 = findViewById<Button>(R.id.yes_button_12)
//        question12.setOnClickListener {
//            if (!moduleReccomendations.contains("Hallucinations & Delusions")) {
//                moduleReccomendations.add("Hallucinations & Delusions")
//            }
//            else{
//                moduleReccomendations.remove("Hallucinations & Delusions")
//            }
//        }
//
//        question13 = findViewById<Button>(R.id.yes_button_13)
//        question13.setOnClickListener {
//            if (!moduleReccomendations.contains("Hallucinations & Delusions")) {
//                moduleReccomendations.add("Hallucinations & Delusions")
//            }
//            else{
//                moduleReccomendations.remove("Hallucinations & Delusions")
//            }
//        }
//
//        question14 = findViewById<Button>(R.id.yes_button_14)
//        question14.setOnClickListener {
//            if (!moduleReccomendations.contains("Incontinence")) {
//                moduleReccomendations.add("Incontinence")
//            }
//            else{
//                moduleReccomendations.remove("Incontinence")
//            }
//        }
//
//        question15 = findViewById<Button>(R.id.yes_button_15)
//        question15.setOnClickListener {
//            if (!moduleReccomendations.contains("Sleeping")) {
//                moduleReccomendations.add("Sleeping")
//            }
//            else{
//                moduleReccomendations.remove("Sleeping")
//            }
//        }
//
//        question16 = findViewById<Button>(R.id.yes_button_16)
//        question16.setOnClickListener {
//            if (!moduleReccomendations.contains("Repeating Questions")) {
//                moduleReccomendations.add("Repeated Questions")
//            }
//            else{
//                moduleReccomendations.remove("Repeated Questions")
//            }
//        }
//
//        question17 = findViewById<Button>(R.id.yes_button_17)
//        question17.setOnClickListener {
//            if (!moduleReccomendations.contains("Sexuality")) {
//                moduleReccomendations.add("Sexuality")
//            }
//            else{
//                moduleReccomendations.remove("Sexuality")
//            }
//        }
//
//        question18 = findViewById<Button>(R.id.yes_button_18)
//        question18.setOnClickListener {
//            if (!moduleReccomendations.contains("Shadowing")) {
//                moduleReccomendations.add("Shadowing")
//            }
//            else{
//                moduleReccomendations.remove("Shadowing")
//            }
//        }
//
//        question19 = findViewById<Button>(R.id.yes_button_19)
//        question19.setOnClickListener {
//            if (!moduleReccomendations.contains("Visiting")) {
//                moduleReccomendations.add("Visiting")
//            }
//            else{
//                moduleReccomendations.remove("Visiting")
//            }
//        }
//
//        question20 = findViewById<Button>(R.id.yes_button_20)
//        question20.setOnClickListener {
//            if (!moduleReccomendations.contains("Wandering")) {
//                moduleReccomendations.add("Wandering")
//            }
//            else{
//                moduleReccomendations.remove("Wandering")
//            }
//        }
//
//        question21 = findViewById<Button>(R.id.yes_button_21)
//        question21.setOnClickListener {
//            if (!moduleReccomendations.contains("Driving")) {
//                moduleReccomendations.add("Driving")
//            }
//            else{
//                moduleReccomendations.remove("Driving")
//            }
//        }
//
//        question22 = findViewById<Button>(R.id.yes_button_22)
//        question22.setOnClickListener {
//            if (!moduleReccomendations.contains("Safety")) {
//                moduleReccomendations.add("Safety")
//            }
//            else{
//                moduleReccomendations.remove("Safety")
//            }
//        }
//
//        question23 = findViewById<Button>(R.id.yes_button_23)
//        question23.setOnClickListener {
//            if (!moduleReccomendations.contains("Dental Care")) {
//                moduleReccomendations.add("Dental Care")
//            }
//            else{
//                moduleReccomendations.remove("Dental Care")
//            }
//        }
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        repeatButton = findViewById<Button>(R.id.pick_duration_back)
        repeatButton.setOnClickListener {
            speak(qiContext)
        }

        speak(qiContext)
    }

    fun speak(qiContext: QiContext) {
        val speech: Say = SayBuilder.with(qiContext)
            .withText("Please select yes to any of the following issues you may be having in your caregiving journey.      ")
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