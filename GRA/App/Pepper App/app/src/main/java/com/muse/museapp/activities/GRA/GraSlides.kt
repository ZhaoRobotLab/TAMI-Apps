package com.muse.museapp.activities.GRA

import android.animation.AnimatorSet
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.muse.museapp.R
import com.muse.museapp.activities.Welcome
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import kotlinx.android.synthetic.main.gra_slides.*
import java.util.*


class GraSlides : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var backSlideButton: Button
    private lateinit var nextSlideButton: Button
    private lateinit var mainMenuButton: Button

    private lateinit var slideTitleView: TextView
    private lateinit var slideInfo: TextView

    private lateinit var loadingPanel: View

    private var curSlide = 0
    private val slideTitle = arrayOf("What is colorectal cancer?", "Possible results and their meanings", "What if the results are positive?", "Are my genetic testing results private/protected?", "What happens after testing?")
    // grab module and section from shared preferences
    private var module = "depression"
    private var section = "Caregiver Coping"
    private var slidePrompts = arrayOf("Write a brief intro as to what colorectal cancer is and some of the main causes of it please include additional details on inheritance patterns, penetrance, variable expressivity, and the possibility of genetic heterogeneity, please simplify these concepts to where someone in the 8th grade could understand them. Also please discuss them as if you were a doctor talking to a patient. Do not use big words that the user will not understand, but instead you should explain the topics without naming them. Prioritize explaining the concept and only use a vocabulary term when absolutely necessary", "Write a brief explanation as to what genetic testing is (specifically for colorectal cancer) and explain the possible outcomes of testing including positive (pathogenic, likely pathogenic), negative (including how negative results make the hereditary reason for the cancer much less likely but not impossible), uncertain, or mosaic results and unexpected findings such as a pathogenic variant (PV) in a gene that does not currently explain the patient's personal or family history of cancer, please simplify these concepts to where someone in the 8th grade could understand them. Also please discuss them as if you were a doctor talking to a patient. Do not use big words that the user will not understand, but instead you should explain the topics without naming them. Prioritize explaining the concept and only use a vocabulary term when absolutely necessary", "Write a brief section discussing possible management options for colorectal cancer if a Pathogenic/Likely Pathogenic variant is identified (ie, enhanced surveillance, risk-reducing chemo-preventive agents, risk-reducing surgery), please simplify these concepts to where someone in the 8th grade could understand them. Also please discuss them as if you were a doctor talking to a patient. Do not use big words that the user will not understand, but instead you should explain the topics without naming them. Prioritize explaining the concept and only use a vocabulary term when absolutely necessary", "Write a brief section discussing current legislation regarding genetic discrimination and privacy of genetic information (eg, Genetic Information Nondiscrimination Act of 2008 [GINA]) and whether or not a patient's genetic information is safe. Include that the patient should discuss information regarding insurance and privacy with their clinician further. Please simplify these concepts to where someone in the 8th grade could understand them. Also please discuss them as if you were a doctor talking to a patient. Do not use big words that the user will not understand, but instead you should explain the topics without naming them. Prioritize explaining the concept and only use a vocabulary term when absolutely necessary", "Write a section discussing various options for genetic testing results disclosure, including patient consent for possibility of releasing results to the patient’s relative or other designated individual if necessary. Also, discuss the financial costs of genetic counseling and testing. Please direct the patient to ask their clinician for more information especially for anything that you would need more information to know for certain of. Additionally, explain to the patient that if they choose to proceed with testing, their provider will be contacting them with their results within 3-4 weeks. Also please briefly explain how testing is normally performed with bloodwork or a saliva sample. Initially these results will be reviewed over the phone, but a follow up appointment will be available to review results in person should the patient wish it")
    private val responses = mutableListOf<String>()

    private val name = UserData.userName
    
//    private lateinit var mp1: MediaPlayer
//    private lateinit var mp2: MediaPlayer
    private lateinit var timer: Timer 
    private lateinit var animatorSet: AnimatorSet
//    private lateinit var countDownTimer: CountDownTimer

    private var duration = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gra_slides)
        QiSDK.register(this, this)

        pythonStart()
        setButtons()

        slideTitleView = findViewById<TextView>(R.id.slideTitle)
        slideInfo = findViewById<TextView>(R.id.slideInfo)

        loadingPanel = findViewById(R.id.loadingPanel)
        loadingPanel.visibility = View.INVISIBLE

        slideTitleView.text = "Load Presentation"
        slideInfo.text = "Click next slide to generate and load presentation. This may take a few seconds."

        val sharedPref = getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
        module = sharedPref.getString("module", "depression").toString()
        section = sharedPref.getString("section", "Caregiver Coping").toString()
        slidePrompts = arrayOf("Write a brief intro as to what colorectal cancer is and some of the main causes of it please include additional details on inheritance patterns, penetrance, variable expressivity, and the possibility of genetic heterogeneity, please simplify these concepts to where someone in the 8th grade could understand them. Also please discuss them as if you were a doctor talking to a patient. Do not use big words that the user will not understand, but instead you should explain the topics without naming them. Prioritize explaining the concept and only use a vocabulary term when absolutely necessary", "Write a brief explanation as to what genetic testing is (specifically for colorectal cancer) and explain the possible outcomes of testing including positive (pathogenic, likely pathogenic), negative (including how negative results make the hereditary reason for the cancer much less likely but not impossible), uncertain, or mosaic results and unexpected findings such as a pathogenic variant (PV) in a gene that does not currently explain the patient's personal or family history of cancer, please simplify these concepts to where someone in the 8th grade could understand them. Also please discuss them as if you were a doctor talking to a patient. Do not use big words that the user will not understand, but instead you should explain the topics without naming them. Prioritize explaining the concept and only use a vocabulary term when absolutely necessary", "Write a brief section discussing possible management options for colorectal cancer if a Pathogenic/Likely Pathogenic variant is identified (ie, enhanced surveillance, risk-reducing chemo-preventive agents, risk-reducing surgery), please simplify these concepts to where someone in the 8th grade could understand them. Also please discuss them as if you were a doctor talking to a patient. Do not use big words that the user will not understand, but instead you should explain the topics without naming them. Prioritize explaining the concept and only use a vocabulary term when absolutely necessary", "Write a brief section discussing current legislation regarding genetic discrimination and privacy of genetic information (eg, Genetic Information Nondiscrimination Act of 2008 [GINA]) and whether or not a patient's genetic information is safe. Include that the patient should discuss information regarding insurance and privacy with their clinician further. Please simplify these concepts to where someone in the 8th grade could understand them. Also please discuss them as if you were a doctor talking to a patient. Do not use big words that the user will not understand, but instead you should explain the topics without naming them. Prioritize explaining the concept and only use a vocabulary term when absolutely necessary", "Write a section discussing various options for genetic testing results disclosure, including patient consent for possibility of releasing results to the patient’s relative or other designated individual if necessary. Also, discuss the financial costs of genetic counseling and testing. Please direct the patient to ask their clinician for more information especially for anything that you would need more information to know for certain of. Additionally, explain to the patient that if they choose to proceed with testing, their provider will be contacting them with their results within 3-4 weeks. Also please briefly explain how testing is normally performed with bloodwork or a saliva sample. Initially these results will be reviewed over the phone, but a follow up appointment will be available to review results in person should the patient wish it")
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
        nextButton = findViewById<Button>(R.id.rhythm_practice_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, GraQnA::class.java)
            intent.putExtra("duration", duration)
            startActivity(intent)
        }

        mainMenuButton = findViewById<Button>(R.id.rhythm_practice_mainmenu1)
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
        return pythonFile.callAttr("slide", prompt).toString()
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
        val intent = Intent(this, GraQnA::class.java)
        intent.putExtra("duration", duration)
        startActivity(intent)
    }


    override fun onRobotFocusGained(qiContext: QiContext) {
        runOnUiThread{
            backSlideButton = findViewById<Button>(R.id.rhythm_practice_next3)
            backSlideButton.setOnClickListener {
                // set slideCurTitle and slideCurResponse back one
                if (responses.size != 0){
                    if (curSlide > 0) {
                        slideTitleView.text = slideTitle[curSlide - 1]
                        slideInfo.text = "${responses[curSlide - 1]}\n"
                        curSlide -= 1
                    }
                }
            }

            var curTitle = ""
            var curResponse = ""

            nextSlideButton = findViewById<Button>(R.id.rhythm_practice_next1)
            nextSlideButton.setOnClickListener {
                // show loading indicator
                loadingPanel.visibility = View.VISIBLE

                // change button color to green
                nextSlideButton.setBackgroundColor(
                    Color.GREEN)

                // set slideCurTitle and slideCurResponse forward one
                if (responses.isEmpty()) {
                    Thread {
                        grabResponses()

                        runOnUiThread {
                            initializeSlide()
                            curTitle = slideTitle[curSlide]
                            curResponse = responses[curSlide]

                            speak(qiContext, "This page is titled $curTitle!            $curResponse!             ")

                            // Hide Panel after finished loading
                            loadingPanel.visibility = View.INVISIBLE
                        }
                    }.start()
                } else if (curSlide < slideTitle.size - 1) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        slideTitleView.text = slideTitle[curSlide + 1]
                        slideInfo.text = responses[curSlide + 1] + "\n"
                        curSlide += 1
                        curTitle = slideTitle[curSlide]
                        curResponse = responses[curSlide]

                        speak(
                            qiContext,
                            "This page is titled $curTitle!            $curResponse!             "
                        )

                        // Hide loading indicator after slide change
                        loadingPanel.visibility = View.INVISIBLE
                    }, 3000)
                }
                nextButton.setBackgroundColor(Color.parseColor("#3F51B5"))
            }
        }
        speak(qiContext,
            "Hello $name  Welcome to the Presentation Module!         Tap the next slide button to generate and load your presentation.        Once it is generated navigate through the slides with the next slide and last slide buttons!          When done with presentation portion click next page to continue!      Please wait a few seconds after hitting the    next slide   button as I generate the slides for you "
        )
    }

    fun speak(qiContext: QiContext, response: String) {
        val phrase = Phrase("\\rspd=77\\ \\vct=72\\ $response");
        val say: Future<Say> = SayBuilder.with(qiContext)
            .withPhrase(
                phrase
            )
            .buildAsync()
        say.andThenConsume {
            it.async().run()
        }
    }

    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
    }

    override fun onRobotFocusRefused(reason: String?) {
        TODO("Not yet implemented")
    }
}
