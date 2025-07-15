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

class ChooseModule : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var submitButton: Button
    private lateinit var backButton: Button
    private lateinit var repeatButton: Button
    private lateinit var mainMenuButton: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var button10: Button
    private lateinit var button11: Button
    private lateinit var button12: Button
    private lateinit var button13: Button
    private lateinit var button14: Button
    private lateinit var button15: Button
    private lateinit var button16: Button
    private lateinit var button17: Button
    private lateinit var button18: Button
    private lateinit var button19: Button
    private lateinit var button20: Button
    private lateinit var button21: Button
    private lateinit var button22: Button
    private lateinit var button23: Button
    private lateinit var button24: Button
    private lateinit var button25: Button
    private lateinit var button26: Button
    private lateinit var button27: Button
    private lateinit var button28: Button
    private lateinit var button29: Button
    private lateinit var button30: Button
    private lateinit var button31: Button
    private lateinit var button32: Button
    private lateinit var button33: Button
    private lateinit var button34: Button
    private lateinit var button35: Button
    private lateinit var button36: Button
    private lateinit var button37: Button
    private lateinit var button38: Button
    private lateinit var button39: Button
    private lateinit var button40: Button
    private lateinit var button41: Button
    private lateinit var button42: Button
    private lateinit var button43: Button
    private lateinit var button44: Button
    private lateinit var button45: Button
    private lateinit var button46: Button
    private lateinit var button47: Button
    private lateinit var button48: Button
    private lateinit var chosenModule: TextView

    private var duration = 0
    private var section = ""
    private var module = ""

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.choose_module)
    }

    override fun onResume() {
        super.onResume()
        setButtons()
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
        backButton = findViewById<Button>(R.id.rhythm_transition1_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        mainMenuButton = findViewById<Button>(R.id.rhythm_transition1_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }

        chosenModule = findViewById<TextView>(R.id.textView6)

        // grab moduleReccomendations from shared preferences
        val sharedPref = getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
        val editor = sharedPref.edit()
        val moduleReccomendations = sharedPref.getStringSet("moduleReccomendations", null)

        // for buttons 1-18 set shared preferences variable "section" to "Caregiver Coping" and "module" to the the buttons text
        // for buttons 19-48 set shared preferences variable "section" to "Behavorial Issues" and "module" to the the buttons text
        button1 = findViewById<Button>(R.id.button)
        if (moduleReccomendations != null && moduleReccomendations.contains(button1.text.toString())) {
            button1.text = "Recommended: " + button1.text
        }
        button1.setOnClickListener {
            if (button1.text.contains("Recommended: ")) {
                module = button1.text.toString().substring(13)
            } else {
                module = button1.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button2 = findViewById<Button>(R.id.button2)
        if (moduleReccomendations != null && moduleReccomendations.contains(button2.text.toString())) {
            button2.text = "Recommended: " + button2.text
        }
        button2.setOnClickListener {
            if (button2.text.contains("Recommended: ")) {
                module = button2.text.toString().substring(13)
            } else {
                module = button2.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button3 = findViewById<Button>(R.id.button3)
        if (moduleReccomendations != null && moduleReccomendations.contains(button3.text.toString())) {
            button3.text = "Recommended: " + button3.text
        }
        button3.setOnClickListener {
            if (button3.text.contains("Recommended: ")) {
                module = button3.text.toString().substring(13)
            } else {
                module = button3.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button4 = findViewById<Button>(R.id.button4)
        if (moduleReccomendations != null && moduleReccomendations.contains(button4.text.toString())) {
            button4.text = "Recommended: " + button4.text
        }
        button4.setOnClickListener {
            if (button4.text.contains("Recommended: ")) {
                module = button4.text.toString().substring(13)
            } else {
                module = button4.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button5 = findViewById<Button>(R.id.button5)
        if (moduleReccomendations != null && moduleReccomendations.contains(button5.text.toString())) {
            button5.text = "Recommended: " + button5.text
        }
        button5.setOnClickListener {
            if (button5.text.contains("Recommended: ")) {
                module = button5.text.toString().substring(13)
            } else {
                module = button5.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button6 = findViewById<Button>(R.id.button6)
        if (moduleReccomendations != null && moduleReccomendations.contains(button6.text.toString())) {
            button6.text = "Recommended: " + button6.text
        }
        button6.setOnClickListener {
            if (button6.text.contains("Recommended: ")) {
                module = button6.text.toString().substring(13)
            } else {
                module = button6.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button7 = findViewById<Button>(R.id.button7)
        if (moduleReccomendations != null && moduleReccomendations.contains(button7.text.toString())) {
            button7.text = "Recommended: " + button7.text
        }
        button7.setOnClickListener {
            if (button7.text.contains("Recommended: ")) {
                module = button7.text.toString().substring(13)
            } else {
                module = button7.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button8 = findViewById<Button>(R.id.button8)
        if (moduleReccomendations != null && moduleReccomendations.contains(button8.text.toString())) {
            button8.text = "Recommended: " + button8.text
        }
        button8.setOnClickListener {
            if (button8.text.contains("Recommended: ")) {
                module = button8.text.toString().substring(13)
            } else {
                module = button8.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button9 = findViewById<Button>(R.id.button9)
        if (moduleReccomendations != null && moduleReccomendations.contains(button9.text.toString())) {
            button9.text = "Recommended: " + button9.text
        }
        button9.setOnClickListener {
            if (button9.text.contains("Recommended: ")) {
                module = button9.text.toString().substring(13)
            } else {
                module = button9.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button10 = findViewById<Button>(R.id.button10)
        if (moduleReccomendations != null && moduleReccomendations.contains(button10.text.toString())) {
            button10.text = "Recommended: " + button10.text
        }
        button10.setOnClickListener {
            if (button10.text.contains("Recommended: ")) {
                module = button10.text.toString().substring(13)
            } else {
                module = button10.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button11 = findViewById<Button>(R.id.button11)
        if (moduleReccomendations != null && moduleReccomendations.contains(button11.text.toString())) {
            button11.text = "Recommended: " + button11.text
        }
        button11.setOnClickListener {
            if (button11.text.contains("Recommended: ")) {
                module = button11.text.toString().substring(13)
            } else {
                module = button11.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button12 = findViewById<Button>(R.id.button12)
        if (moduleReccomendations != null && moduleReccomendations.contains(button12.text.toString())) {
            button12.text = "Recommended: " + button12.text
        }
        button12.setOnClickListener {
            if (button12.text.contains("Recommended: ")) {
                module = button12.text.toString().substring(13)
            } else {
                module = button12.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button13 = findViewById<Button>(R.id.button13)
        if (moduleReccomendations != null && moduleReccomendations.contains(button13.text.toString())) {
            button13.text = "Recommended: " + button13.text
        }
        button13.setOnClickListener {
            if (button13.text.contains("Recommended: ")) {
                module = button13.text.toString().substring(13)
            } else {
                module = button13.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button14 = findViewById<Button>(R.id.button14)
        if (moduleReccomendations != null && moduleReccomendations.contains(button14.text.toString())) {
            button14.text = "Recommended: " + button14.text
        }
        button14.setOnClickListener {
            if (button14.text.contains("Recommended: ")) {
                module = button14.text.toString().substring(13)
            } else {
                module = button14.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button15 = findViewById<Button>(R.id.button15)
        if (moduleReccomendations != null && moduleReccomendations.contains(button15.text.toString())) {
            button15.text = "Recommended: " + button15.text
        }
        button15.setOnClickListener {
            if (button15.text.contains("Recommended: ")) {
                module = button15.text.toString().substring(13)
            } else {
                module = button15.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button16 = findViewById<Button>(R.id.button16)
        if (moduleReccomendations != null && moduleReccomendations.contains(button16.text.toString())) {
            button16.text = "Recommended: " + button16.text
        }
        button16.setOnClickListener {
            if (button16.text.contains("Recommended: ")) {
                module = button16.text.toString().substring(13)
            } else {
                module = button16.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button17 = findViewById<Button>(R.id.button17)
        if (moduleReccomendations != null && moduleReccomendations.contains(button17.text.toString())) {
            button17.text = "Recommended: " + button17.text
        }
        button17.setOnClickListener {
            if (button17.text.contains("Recommended: ")) {
                module = button17.text.toString().substring(13)
            } else {
                module = button17.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button18 = findViewById<Button>(R.id.button18)
        if (moduleReccomendations != null && moduleReccomendations.contains(button18.text.toString())) {
            button18.text = "Recommended: " + button18.text
        }
        button18.setOnClickListener {
            if (button18.text.contains("Recommended: ")) {
                module = button18.text.toString().substring(13)
            } else {
                module = button18.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Caregiver Coping"
            setupSubmitButton()
        }

        button19 = findViewById<Button>(R.id.button19)
        if (moduleReccomendations != null && moduleReccomendations.contains(button19.text.toString())) {
            button19.text = "Recommended: " + button19.text
        }
        button19.setOnClickListener {
            if (button19.text.contains("Recommended: ")) {
                module = button19.text.toString().substring(13)
            } else {
                module = button19.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button20 = findViewById<Button>(R.id.button20)
        if (moduleReccomendations != null && moduleReccomendations.contains(button20.text.toString())) {
            button20.text = "Recommended: " + button20.text
        }
        button20.setOnClickListener {
            if (button20.text.contains("Recommended: ")) {
                module = button20.text.toString().substring(13)
            } else {
                module = button20.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button21 = findViewById<Button>(R.id.button21)
        if (moduleReccomendations != null && moduleReccomendations.contains(button21.text.toString())) {
            button21.text = "Recommended: " + button21.text
        }
        button21.setOnClickListener {
            if (button21.text.contains("Recommended: ")) {
                module = button21.text.toString().substring(13)
            } else {
                module = button21.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button22 = findViewById<Button>(R.id.button22)
        if (moduleReccomendations != null && moduleReccomendations.contains(button22.text.toString())) {
            button22.text = "Recommended: " + button22.text
        }
        button22.setOnClickListener {
            if (button22.text.contains("Recommended: ")) {
                module = button22.text.toString().substring(13)
            } else {
                module = button22.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button23 = findViewById<Button>(R.id.button23)
        if (moduleReccomendations != null && moduleReccomendations.contains(button23.text.toString())) {
            button23.text = "Recommended: " + button23.text
        }
        button23.setOnClickListener {
            if (button23.text.contains("Recommended: ")) {
                module = button23.text.toString().substring(13)
            } else {
                module = button23.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button24 = findViewById<Button>(R.id.button24)
        if (moduleReccomendations != null && moduleReccomendations.contains(button24.text.toString())) {
            button24.text = "Recommended: " + button24.text
        }
        button24.setOnClickListener {
            if (button24.text.contains("Recommended: ")) {
                module = button24.text.toString().substring(13)
            } else {
                module = button24.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button25 = findViewById<Button>(R.id.button25)
        if (moduleReccomendations != null && moduleReccomendations.contains(button25.text.toString())) {
            button25.text = "Recommended: " + button25.text
        }
        button25.setOnClickListener {
            if (button25.text.contains("Recommended: ")) {
                module = button25.text.toString().substring(13)
            } else {
                module = button25.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button26 = findViewById<Button>(R.id.button26)
        if (moduleReccomendations != null && moduleReccomendations.contains(button26.text.toString())) {
            button26.text = "Recommended: " + button26.text
        }
        button26.setOnClickListener {
            if (button26.text.contains("Recommended: ")) {
                module = button26.text.toString().substring(13)
            } else {
                module = button26.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button27 = findViewById<Button>(R.id.button27)
        if (moduleReccomendations != null && moduleReccomendations.contains(button27.text.toString())) {
            button27.text = "Recommended: " + button27.text
        }
        button27.setOnClickListener {
            if (button27.text.contains("Recommended: ")) {
                module = button27.text.toString().substring(13)
            } else {
                module = button27.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button28 = findViewById<Button>(R.id.button28)
        if (moduleReccomendations != null && moduleReccomendations.contains(button28.text.toString())) {
            button28.text = "Recommended: " + button28.text
        }
        button28.setOnClickListener {
            if (button28.text.contains("Recommended: ")) {
                module = button28.text.toString().substring(13)
            } else {
                module = button28.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button29 = findViewById<Button>(R.id.button29)
        if (moduleReccomendations != null && moduleReccomendations.contains(button29.text.toString())) {
            button29.text = "Recommended: " + button29.text
        }
        button29.setOnClickListener {
            if (button29.text.contains("Recommended: ")) {
                module = button29.text.toString().substring(13)
            } else {
                module = button29.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button30 = findViewById<Button>(R.id.button30)
        if (moduleReccomendations != null && moduleReccomendations.contains(button30.text.toString())) {
            button30.text = "Recommended: " + button30.text
        }
        button30.setOnClickListener {
            if (button30.text.contains("Recommended: ")) {
                module = button30.text.toString().substring(13)
            } else {
                module = button30.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button31 = findViewById<Button>(R.id.button31)
        if (moduleReccomendations != null && moduleReccomendations.contains(button31.text.toString())) {
            button31.text = "Recommended: " + button31.text
        }
        button31.setOnClickListener {
            if (button31.text.contains("Recommended: ")) {
                module = button31.text.toString().substring(13)
            } else {
                module = button31.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button32 = findViewById<Button>(R.id.button32)
        if (moduleReccomendations != null && moduleReccomendations.contains(button32.text.toString())) {
            button32.text = "Recommended: " + button32.text
        }
        button32.setOnClickListener {
            if (button32.text.contains("Recommended: ")) {
                module = button32.text.toString().substring(13)
            } else {
                module = button32.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button33 = findViewById<Button>(R.id.button33)
        if (moduleReccomendations != null && moduleReccomendations.contains(button33.text.toString())) {
            button33.text = "Recommended: " + button33.text
        }
        button33.setOnClickListener {
            if (button33.text.contains("Recommended: ")) {
                module = button33.text.toString().substring(13)
            } else {
                module = button33.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button34 = findViewById<Button>(R.id.button34)
        if (moduleReccomendations != null && moduleReccomendations.contains(button34.text.toString())) {
            button34.text = "Recommended: " + button34.text
        }
        button34.setOnClickListener {
            if (button34.text.contains("Recommended: ")) {
                module = button34.text.toString().substring(13)
            } else {
                module = button34.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button35 = findViewById<Button>(R.id.button35)
        if (moduleReccomendations != null && moduleReccomendations.contains(button35.text.toString())) {
            button35.text = "Recommended: " + button35.text
        }
        button35.setOnClickListener {
            if (button35.text.contains("Recommended: ")) {
                module = button35.text.toString().substring(13)
            } else {
                module = button35.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button36 = findViewById<Button>(R.id.button36)
        if (moduleReccomendations != null && moduleReccomendations.contains(button36.text.toString())) {
            button36.text = "Recommended: " + button36.text
        }
        button36.setOnClickListener {
            if (button36.text.contains("Recommended: ")) {
                module = button36.text.toString().substring(13)
            } else {
                module = button36.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button37 = findViewById<Button>(R.id.button37)
        if (moduleReccomendations != null && moduleReccomendations.contains(button37.text.toString())) {
            button37.text = "Recommended: " + button37.text
        }
        button37.setOnClickListener {
            if (button37.text.contains("Recommended: ")) {
                module = button37.text.toString().substring(13)
            } else {
                module = button37.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button38 = findViewById<Button>(R.id.button38)
        if (moduleReccomendations != null && moduleReccomendations.contains(button38.text.toString())) {
            button38.text = "Recommended: " + button38.text
        }
        button38.setOnClickListener {
            if (button38.text.contains("Recommended: ")) {
                module = button38.text.toString().substring(13)
            } else {
                module = button38.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button39 = findViewById<Button>(R.id.button39)
        if (moduleReccomendations != null && moduleReccomendations.contains(button39.text.toString())) {
            button39.text = "Recommended: " + button39.text
        }
        button39.setOnClickListener {
            if (button39.text.contains("Recommended: ")) {
                module = button39.text.toString().substring(13)
            } else {
                module = button39.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button40 = findViewById<Button>(R.id.button40)
        if (moduleReccomendations != null && moduleReccomendations.contains(button40.text.toString())) {
            button40.text = "Recommended: " + button40.text
        }
        button40.setOnClickListener {
            if (button40.text.contains("Recommended: ")) {
                module = button40.text.toString().substring(13)
            } else {
                module = button40.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button41 = findViewById<Button>(R.id.button41)
        if (moduleReccomendations != null && moduleReccomendations.contains(button41.text.toString())) {
            button41.text = "Recommended: " + button41.text
        }
        button41.setOnClickListener {
            if (button41.text.contains("Recommended: ")) {
                module = button41.text.toString().substring(13)
            } else {
                module = button41.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button42 = findViewById<Button>(R.id.button42)
        if (moduleReccomendations != null && moduleReccomendations.contains(button42.text.toString())) {
            button42.text = "Recommended: " + button42.text
        }
        button42.setOnClickListener {
            if (button42.text.contains("Recommended: ")) {
                module = button42.text.toString().substring(13)
            } else {
                module = button42.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button43 = findViewById<Button>(R.id.button43)
        if (moduleReccomendations != null && moduleReccomendations.contains(button43.text.toString())) {
            button43.text = "Recommended: " + button43.text
        }
        button43.setOnClickListener {
            if (button43.text.contains("Recommended: ")) {
                module = button43.text.toString().substring(13)
            } else {
                module = button43.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button44 = findViewById<Button>(R.id.button44)
        if (moduleReccomendations != null && moduleReccomendations.contains(button44.text.toString())) {
            button44.text = "Recommended: " + button44.text
        }
        button44.setOnClickListener {
            if (button44.text.contains("Recommended: ")) {
                module = button44.text.toString().substring(13)
            } else {
                module = button44.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button45 = findViewById<Button>(R.id.button45)
        if (moduleReccomendations != null && moduleReccomendations.contains(button45.text.toString())) {
            button45.text = "Recommended: " + button45.text
        }
        button45.setOnClickListener {
            if (button45.text.contains("Recommended: ")) {
                module = button45.text.toString().substring(13)
            } else {
                module = button45.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button46 = findViewById<Button>(R.id.button46)
        if (moduleReccomendations != null && moduleReccomendations.contains(button46.text.toString())) {
            button46.text = "Recommended: " + button46.text
        }
        button46.setOnClickListener {
            if (button46.text.contains("Recommended: ")) {
                module = button46.text.toString().substring(13)
            } else {
                module = button46.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button47 = findViewById<Button>(R.id.button47)
        if (moduleReccomendations != null && moduleReccomendations.contains(button47.text.toString())) {
            button47.text = "Recommended: " + button47.text
        }
        button47.setOnClickListener {
            if (button47.text.contains("Recommended: ")) {
                module = button47.text.toString().substring(13)
            } else {
                module = button47.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }

        button48 = findViewById<Button>(R.id.button48)
        if (moduleReccomendations != null && moduleReccomendations.contains(button48.text.toString())) {
            button48.text = "Recommended: " + button48.text
        }
        button48.setOnClickListener {
            if (button48.text.contains("Recommended: ")) {
                module = button48.text.toString().substring(13)
            } else {
                module = button48.text.toString()
            }
            chosenModule.text = "Please pick a Module to begin\n Chosen Module:\n" + module
            section = "Behavorial Issues"
            setupSubmitButton()
        }
    }

    fun setupSubmitButton() {
        submitButton = findViewById<Button>(R.id.sing_finish_next2)
        submitButton.setOnClickListener {
            val sharedPref = getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.apply {
                putString("section", "$section")
                putString("module", "$module")
                apply()
            }
            println("section: $section")
            println("module: $module")
            val intent = Intent(this, RiseSlides::class.java)
            intent.putExtra("duration", duration)
            startActivity(intent)
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        repeatButton = findViewById<Button>(R.id.rhythm_transition1_repeat)
        repeatButton.setOnClickListener {
            speak(qiContext)
        }

        speak(qiContext)
    }

    fun speak(qiContext: QiContext) {
        val speech: Say = SayBuilder.with(qiContext)
            .withText("Please pick a Knowledge Module to begin.      " +
                    "Recommended modules are marked with the word 'Recommended'.       " +
                    "You can scroll through all possible choices in the middle of the screen.       " +
                    "After clicking your chosen module click the Submit button in the bottom right to continue!")
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