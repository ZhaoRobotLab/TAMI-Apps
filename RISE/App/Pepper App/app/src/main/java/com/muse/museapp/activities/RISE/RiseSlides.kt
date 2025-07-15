package com.muse.museapp.activities.RISE

import android.animation.AnimatorSet
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.muse.museapp.R
import com.muse.museapp.activities.Welcome
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import java.util.*


class RiseSlides : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var backSlideButton: Button
    private lateinit var nextSlideButton: Button
    private lateinit var mainMenuButton: Button

    private lateinit var slideTitleView: TextView
    private lateinit var slideInfo: TextView

    private lateinit var loadingPanel: View

    private var curSlide = 0
    private val slideTitle = arrayOf("Introduction to module", "Basics and Summary", "Real Life Examples", "Some Different Approaches")
    // grab module and section from shared preferences
    private var module = "depression"
    private var section = "Caregiver Coping"
    private var slidePrompts = arrayOf("Write a brief and short introduction to the module $module from section $section do not include the title of the module, and try to shorten the intro as much as possible. Please simplify the the material to where an 8th grader can understand it. Also please refrain to referring to people who are advanced in age as 'elderly'", "Please simplify the the material to where an 8th grader can understand it. Also please refrain to referring to people who are advanced in age as 'elderly'. Please Write the basic info that a caregiver should know from the module $module from section $section make it as concise, short and easy to read as possible, limit to 5 sentences, number each thought like: 1., 2., 3.", "Please simplify the the material to where an 8th grader can understand it. Also please refrain to referring to people who are advanced in age as 'elderly'. Quickly and efficiently list 2 common issues and solutions to them that involve the module $module from section $section number them like: 1., 2., 3., Limit to four sentences in total response", "Please simplify the the material to where an 8th grader can understand it. Also please refrain to referring to people who are advanced in age as 'elderly'. Talk very briefly and efficiently in 6th grade reading level about 4 unique approaches and different outlooks on problems usually found in the $module module from section $section number each new approach and follow with its name like: 1. APPROACH, Also keep the explanations of each approach to 1 sentence each.")
    private val responses = mutableListOf<String>()

    private lateinit var timer: Timer
    private lateinit var animatorSet: AnimatorSet

    private var duration = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rise_slides)
        QiSDK.register(this, this)
        pythonStart()
        setButtons()
        slideTitleView = findViewById(R.id.slideTitle)
        slideInfo = findViewById(R.id.slideInfo)
        loadingPanel = findViewById(R.id.loadingPanel)
        loadingPanel.visibility = View.INVISIBLE

        slideTitleView.text = "Load Presentation"
        slideInfo.text = "Click next slide to generate and load presentation. This may take a few seconds."
        val sharedPref = getSharedPreferences("PREFERENCE_NAME", MODE_PRIVATE)
        module = sharedPref.getString("module", "depression").toString()
        section = sharedPref.getString("section", "Caregiver Coping").toString()
        slidePrompts = arrayOf("Write a brief and short introduction to the module $module from section $section do not include the title of the module, and try to shorten the intro as much as possible. Please simplify the the material to where an 8th grader can understand it. Also please refrain to referring to people who are advanced in age as 'elderly'", "Please simplify the the material to where an 8th grader can understand it. Also please refrain to referring to people who are advanced in age as 'elderly'. Please Write the basic info that a caregiver should know from the module $module from section $section make it as concise, short and easy to read as possible, limit to 5 sentences, number each thought like: 1., 2., 3.", "Please simplify the the material to where an 8th grader can understand it. Also please refrain to referring to people who are advanced in age as 'elderly'. Quickly and efficiently list 2 common issues and solutions to them that involve the module $module from section $section number them like: 1., 2., 3., Limit to four sentences in total response", "Please simplify the the material to where an 8th grader can understand it. Also please refrain to referring to people who are advanced in age as 'elderly'. Talk very briefly and efficiently in 6th grade reading level about 4 unique approaches and different outlooks on problems usually found in the $module module from section $section number each new approach and follow with its name like: 1. APPROACH, Also keep the explanations of each approach to 1 sentence each.")
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
        nextButton = findViewById(R.id.rhythm_practice_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, RiseQnA::class.java)
            intent.putExtra("duration", duration)
            startActivity(intent)
        }

        mainMenuButton = findViewById(R.id.rhythm_practice_mainmenu1)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    private fun pythonStart() {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
    }

    private fun getResponse(prompt: String?): String {
        val pythonFile = Python.getInstance().getModule("queryscript")
        return pythonFile.callAttr("response", prompt).toString()
    }

    private fun grabResponses() {
        for (i in slidePrompts.indices) {
            responses.add(getResponse(slidePrompts[i]))
        }
    }

    private fun initializeSlide() {
        slideTitleView.text = slideTitle[0]
        slideInfo.text = responses[0]
    }

    fun automaticTransition() {
        val intent = Intent(this, RiseQnA::class.java)
        intent.putExtra("duration", duration)
        startActivity(intent)
    }

//    private fun showLoadingIndicator() {
//        loadingPanel.visibility = View.VISIBLE
//    }

//    private fun hideLoadingIndicator() {
//        loadingPanel.visibility = View.GONE
//    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        runOnUiThread {
            backSlideButton = findViewById(R.id.rhythm_practice_next3)
            backSlideButton.setOnClickListener {
                // set slideCurTitle and slideCurResponse back one
                if (responses.isNotEmpty() && curSlide > 0) {
                    slideTitleView.text = slideTitle[curSlide - 1]
                    slideInfo.text = "${responses[curSlide - 1]}\n"
                    curSlide -= 1
                }
            }

            var curTitle = ""
            var curResponse = ""

            nextSlideButton = findViewById(R.id.rhythm_practice_next1)
            nextSlideButton.setOnClickListener {
                // Show loading indicator
                loadingPanel.visibility = View.VISIBLE

                // change button color to green
//                nextSlideButton.setBackgroundColor(Color.GREEN)
                nextSlideButton.setBackgroundColor(ContextCompat.getColor(this, R.color.basic_green)) // Set color to green
                // set slideCurTitle and slideCurResponse forward one
                if (responses.isEmpty()) {


                    Thread {
                        grabResponses()
                        runOnUiThread {
                            initializeSlide()
                            curTitle = slideTitle[curSlide]
                            curResponse = responses[curSlide]

                            speak(qiContext, "This page is titled $curTitle!            $curResponse!            ")

                            // Hide loading indicator after grabbing responses
                            loadingPanel.visibility = View.INVISIBLE
                        }
                    }.start()


//                    grabResponses()
//                    initializeSlide()
//                    curTitle = slideTitle[curSlide]
//                    curResponse = responses[curSlide]
//
//                    speak(qiContext, "This page is titled $curTitle!            $curResponse!            ")
//
//                    // Hide loading indicator after grabbing responses
//                    loadingPanel.visibility = View.INVISIBLE
                } else if (curSlide < slideTitle.size - 1) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        slideTitleView.text = slideTitle[curSlide + 1]
                        slideInfo.text = "${responses[curSlide + 1]}\n"
                        curSlide += 1
                        curTitle = slideTitle[curSlide]
                        curResponse = responses[curSlide]

                        speak(qiContext, "This page is titled $curTitle!            $curResponse!            ")

                        // Hide loading indicator after slide change
                        loadingPanel.visibility = View.INVISIBLE
                    }, 3000) // Simulate loading time with a delay
                }
                nextButton.setBackgroundColor(Color.parseColor("#3F51B5"))
            }
        }
        speak(qiContext, "Welcome to the Presentation Module!         Tap the next slide button to generate and load your presentation.        Please click the button only once, as it takes me a moment to generate the slides.       Once it is generated navigate through the slides with the next slide and last slide buttons!         When done with presentation portion click next page to continue!")
    }

    fun speak(qiContext: QiContext, response: String) {
        val say: Future<Say> = SayBuilder.with(qiContext)
            .withText(response)
            .buildAsync()
        say.andThenConsume {
            it.async().run()
        }
    }

    override fun onRobotFocusLost() {
    }

    override fun onRobotFocusRefused(reason: String?) {
    }
}
