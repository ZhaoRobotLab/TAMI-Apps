package com.muse.museapp.activities.RISE

import android.content.Intent
import android.graphics.Color
import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat

import android.view.View
import androidx.core.view.isInvisible

import androidx.core.view.isVisible
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.aldebaran.qi.sdk.`object`.conversation.*
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.Future
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.muse.museapp.R
import com.muse.museapp.activities.Welcome



class RiseQuiz : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var mainMenuButton: Button
    private lateinit var nextButton: Button
    private lateinit var question: TextView
    private lateinit var answer1: Button
    private lateinit var answer2: Button
    private lateinit var answer3: Button
    private lateinit var answer4: Button
    private lateinit var loadingPanel: ProgressBar

    private var correct = 0
    private var currentQuestion = 0
    private val questions = mutableListOf<String>()
    private val answers = mutableListOf<String>()
    private val correctAnswers = mutableListOf<String>()

    var module = "depression"
    var section = "Caregiver"
//    private lateinit var countDownTimer: CountDownTimer

    private var duration = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rise_quiz)


        QiSDK.register(this, this)
        pythonStart()

        val sharedPref = getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
        module = sharedPref.getString("module", "depression").toString()
        section = sharedPref.getString("section", "Caregiver Coping").toString()

        question = findViewById<TextView>(R.id.textView36)
        question.setText("Click any Answer to Load/Begin Quiz\nThis may take a few seconds")
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


    private fun pythonStart() {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
    }

    fun automaticTransition() {
        val intent = Intent(this, RiseQuizResults::class.java)
        intent.putExtra("duration", duration)
        startActivity(intent)
    }

    override fun onRobotFocusGained(qiContext: QiContext){
        speak(qiContext, "Welcome to the quiz portions.           This quiz will test your knowledge on the module.            Click any answer to load and begin the quiz.        Please click only once, as it takes me a moment to generate the questions.")

        runOnUiThread{
            answer1 = findViewById<Button>(R.id.rhythm_answer_choice1)
            answer2 = findViewById<Button>(R.id.rhythm_answer_choice2)
            answer3 = findViewById<Button>(R.id.rhythm_answer_choice3)
            answer4 = findViewById<Button>(R.id.rhythm_answer_choice4)
            nextButton = findViewById<Button>(R.id.quiz_next)
            loadingPanel = findViewById(R.id.loadingPanel)
            nextButton.isVisible = false
            loadingPanel.visibility = View.INVISIBLE

            answer1.setText("A1")
            answer2.setText("A2")
            answer3.setText("A3")
            answer4.setText("A4")

            answer1.setOnClickListener {
                answer1.setBackgroundColor(Color.GREEN)
//                loadingPanel.visibility = View.VISIBLE
                loadingPanel.isVisible = true
                createQuiz()
//                loadingPanel.visibility = View.GONE
                loadingPanel.isInvisible = true
                setButtons(qiContext)
                answer1.setBackgroundColor(Color.parseColor("#3F51B5"))
            }
            answer2.setOnClickListener {
                answer2.setBackgroundColor(Color.GREEN)
//                loadingPanel.visibility = View.VISIBLE
                loadingPanel.isVisible = true
                createQuiz()
//                loadingPanel.visibility = View.GONE
                loadingPanel.isInvisible = true
                setButtons(qiContext)
                answer2.setBackgroundColor(Color.parseColor("#3F51B5"))
            }
            answer3.setOnClickListener {
                answer3.setBackgroundColor(Color.GREEN)
//                loadingPanel.visibility = View.VISIBLE
                loadingPanel.isVisible = true
                createQuiz()
//                loadingPanel.visibility = View.GONE
                loadingPanel.isInvisible = true
                setButtons(qiContext)
                answer3.setBackgroundColor(Color.parseColor("#3F51B5"))
            }
            answer4.setOnClickListener {
                answer4.setBackgroundColor(Color.GREEN)
//                loadingPanel.visibility = View.VISIBLE
                loadingPanel.isVisible = true
                createQuiz()
//                loadingPanel.visibility = View.GONE
                loadingPanel.isInvisible = true
                setButtons(qiContext)
                answer4.setBackgroundColor(Color.parseColor("#3F51B5"))
            }
            nextButton.setOnClickListener{
            }
        }
    }

    fun createQuiz() {

//        loadingPanel.visibility = View.VISIBLE

        val pythonFile = Python.getInstance().getModule("queryscript")
        val prompt = "Create 5 follow-up questions for the $module module, make them difficult and thoughtprovoking, do not use the word elderly to describe people with dementia, and dont make the correct answers obivious ensure they are only about and directly involve the module $module. Each question will begin formatted like this 'Q1:'. Give the correct answers to these questions after the question formatted to begin like so: 'Answer:'. Summarize the answers and keep them to less than 15 words. Do not have any other text other than questions and answers"
        val response = pythonFile.callAttr("quizresponse", prompt).toString()
        val fullResponse = pythonFile.callAttr("incorrectAnswers", response).toString()
        println(fullResponse)
        // print the full response
        
        //  What incorrectAnswers does: "Create 3 incorrect answers for the following list of questions and correct answers, make the incorrect answers tricky to where a intelligent person might get the question wrong, but ensure the incorrect answers are incorrect in someway. The questions will afterwards have a total of 4 choices (A-D), ensure there is only be one correct answer and it is the one given, have no questions correct answer be 'all of the above'. Label each answer like: 'A)', and each question like this 'Q1:';. After listing all of the questions and answers give the correct answers formatted like so: 'Answers: A1: A, A2: B';. Mix up which Letter holds the correct answer from A to D. Do not have any other text other than questions, answers, and correct answer list: " + query
        //  break up the response into a 5 length array of questions, a 20 length array of answers, and a 5 length array of correct answers
        //  do this by rotating through the response string
        var question = ""
        var answer = ""
        var i = 0
        var readingQuestion = true
        var readingAnswer = false
        var readingCorrectAnswer = false

        while (i < fullResponse.length) {
            // if readingQuestion is true, add the character to the question string until 'A:' is found then set readingQuestion to false and readingAnswer to true
            if (readingQuestion) {
                if (fullResponse[i] == 'A' && fullResponse[i + 1] == ':') {
                    questions.add(question)
                    question = ""
                    readingQuestion = false
                    readingAnswer = true
                }
                else{
                    question += fullResponse[i]
                }
            }
            if (readingAnswer) {
                if ((answers.size % 4 == 0 && (fullResponse[i] == 'B' && fullResponse[i + 1] == ':')) || ((answers.size-1) % 4 == 0 && (fullResponse[i] == 'C' && fullResponse[i + 1] == ':')) || ((answers.size-2) % 4 == 0 && (fullResponse[i] == 'D' && fullResponse[i + 1] == ':')) || ((answers.size-3) % 4 == 0 && (fullResponse[i] == 'Q' && fullResponse[i + 2] == ':')) || (answers.size == 19 && (fullResponse[i] == 'A' && fullResponse[i + 1] == 'n' && fullResponse[i + 2] == 's' && fullResponse[i + 3] == 'w' && fullResponse[i + 4] == 'e' && fullResponse[i + 5] == 'r' && fullResponse[i + 6] == 's' && fullResponse[i + 7] == ':'))){
                    answers.add(answer)
                    answer = ""
                    if(answers.size % 4 == 0){
                        readingAnswer = false
                        if(answers.size == 20){
                            readingCorrectAnswer = true
                        }
                        else{
                            readingQuestion = true
                        }
                    }
                }
                else{
                    answer += fullResponse[i]
                }
            }
            if (readingCorrectAnswer) {
                if(fullResponse[i] == 'Q' && fullResponse[i + 2] == ':'){
                    correctAnswers.add(fullResponse[i + 4].toString())
                }
            }
            i++
        }

        // rotate through the questions and answers and remove all ':' characters
        for (i in 0 until questions.size) {
            questions[i] = questions[i].replace(":", "")
        }

        for (i in 0 until answers.size) {
            var answer = answers[i]
            // delete the first character of answer
            answers[i] = answer.substring(1)
            answers[i] = answers[i].replace(":", "")
        }
//        loadingPanel.visibility = View.GONE

    }

    fun setButtons(qiContext: QiContext) {
        var currentQuestionStr = ""
        var currentAnswer1 = ""
        var currentAnswer2 = ""
        var currentAnswer3 = ""
        var currentAnswer4 = ""
        mainMenuButton = findViewById<Button>(R.id.rhythm_practice_mainmenu2)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }

        question = findViewById<TextView>(R.id.textView36)
        question.setText(questions[0])

        answer1 = findViewById<Button>(R.id.rhythm_answer_choice1)
        answer2 = findViewById<Button>(R.id.rhythm_answer_choice2)
        answer3 = findViewById<Button>(R.id.rhythm_answer_choice3)
        answer4 = findViewById<Button>(R.id.rhythm_answer_choice4)

        answer1.setText(answers[0])
        answer2.setText(answers[1])
        answer3.setText(answers[2])
        answer4.setText(answers[3])

        currentQuestionStr = questions[0]
        currentAnswer1 = answers[0]
        currentAnswer2 = answers[1]
        currentAnswer3 = answers[2]
        currentAnswer4 = answers[3]

        speak(qiContext, "$currentQuestionStr                Is it: A $currentAnswer1            B $currentAnswer2            C $currentAnswer3            or D $currentAnswer4")

        answer1.setOnClickListener {
            if(correctAnswers[currentQuestion] == "A"){
                correct++
                speak(qiContext, "Correct")
                // set background color to green
                answer1.setBackgroundColor(Color.GREEN)
            }
            else{
                if(correctAnswers[currentQuestion] == "B"){
                    speak(qiContext, "Incorrect       The correct answer is B: $currentAnswer2")
                }
                else if(correctAnswers[currentQuestion] == "C"){
                    speak(qiContext, "Incorrect       The correct answer is C: $currentAnswer3")
                }
                else if(correctAnswers[currentQuestion] == "D"){
                    speak(qiContext, "Incorrect       The correct answer is D: $currentAnswer4")
                }
                // set background color to red
                answer1.setBackgroundColor(Color.RED)
            }
            currentQuestion++
            if(currentQuestion == 5){
                val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
                var editor = sharedPreference.edit()
                var correctStr = correct.toString()
                editor.apply {
                    putString("score", "$correctStr")
                    apply()
                }
                val intent = Intent(this, RiseQuizResults::class.java)
                intent.putExtra("duration", duration)
                startActivity(intent)
            } else {
                // disable the answer buttons
                answer1.isEnabled = false
                answer2.isEnabled = false
                answer3.isEnabled = false
                answer4.isEnabled = false
                // set the next button to visible
                nextButton.isVisible = true
                nextButton.setOnClickListener {
                    question.setText(questions[currentQuestion])
                    answer1.setText(answers[currentQuestion*4])
                    answer2.setText(answers[currentQuestion*4 + 1])
                    answer3.setText(answers[currentQuestion*4 + 2])
                    answer4.setText(answers[currentQuestion*4 + 3])

                    currentQuestionStr = questions[currentQuestion]
                    currentAnswer1 = answers[currentQuestion*4]
                    currentAnswer2 = answers[currentQuestion*4 + 1]
                    currentAnswer3 = answers[currentQuestion*4 + 2]
                    currentAnswer4 = answers[currentQuestion*4 + 3]

                    speak(qiContext, "$currentQuestionStr                Is it: A $currentAnswer1            B $currentAnswer2            C $currentAnswer3            or D $currentAnswer4")

                    answer1.isEnabled = true
                    answer2.isEnabled = true
                    answer3.isEnabled = true
                    answer4.isEnabled = true
                    answer1.setBackgroundColor(Color.parseColor("#3F51B5"))
                    nextButton.isVisible = false
                }
            }
        }
        answer2.setOnClickListener {
            if(correctAnswers[currentQuestion] == "B"){
                correct++
                speak(qiContext, "Correct")
                // set background color to green
                answer2.setBackgroundColor(Color.GREEN)
            }
            else{
                if(correctAnswers[currentQuestion] == "A"){
                    speak(qiContext, "Incorrect       The correct answer is A: $currentAnswer1")
                }
                else if(correctAnswers[currentQuestion] == "C"){
                    speak(qiContext, "Incorrect       The correct answer is C: $currentAnswer3")
                }
                else if(correctAnswers[currentQuestion] == "D"){
                    speak(qiContext, "Incorrect       The correct answer is D: $currentAnswer4")
                }
                // set background color to red
                answer2.setBackgroundColor(Color.RED)
            }
            currentQuestion++
            if(currentQuestion == 5){
                val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
                var editor = sharedPreference.edit()
                var correctStr = correct.toString()
                editor.apply {
                    putString("score", "$correctStr")
                    apply()
                }
                val intent = Intent(this, RiseQuizResults::class.java)
                intent.putExtra("duration", duration)
                startActivity(intent)
            } else{
                // disable the answer buttons
                answer1.isEnabled = false
                answer2.isEnabled = false
                answer3.isEnabled = false
                answer4.isEnabled = false
                // set the next button to visible
                nextButton.isVisible = true
                nextButton.setOnClickListener {
                    question.setText(questions[currentQuestion])
                    answer1.setText(answers[currentQuestion*4])
                    answer2.setText(answers[currentQuestion*4 + 1])
                    answer3.setText(answers[currentQuestion*4 + 2])
                    answer4.setText(answers[currentQuestion*4 + 3])

                    currentQuestionStr = questions[currentQuestion]
                    currentAnswer1 = answers[currentQuestion*4]
                    currentAnswer2 = answers[currentQuestion*4 + 1]
                    currentAnswer3 = answers[currentQuestion*4 + 2]
                    currentAnswer4 = answers[currentQuestion*4 + 3]

                    speak(qiContext, "$currentQuestionStr                Is it: A $currentAnswer1            B $currentAnswer2            C $currentAnswer3            or D $currentAnswer4")

                    answer1.isEnabled = true
                    answer2.isEnabled = true
                    answer3.isEnabled = true
                    answer4.isEnabled = true
                    answer2.setBackgroundColor(Color.parseColor("#3F51B5"))
                    nextButton.isVisible = false
                }
            }
        }
        answer3.setOnClickListener {
            if(correctAnswers[currentQuestion] == "C"){
                correct++
                speak(qiContext, "Correct")
                // set background color to green
                answer3.setBackgroundColor(Color.GREEN)
            }
            else{
                if(correctAnswers[currentQuestion] == "A"){
                    speak(qiContext, "Incorrect       The correct answer is A: $currentAnswer1")
                }
                else if(correctAnswers[currentQuestion] == "B"){
                    speak(qiContext, "Incorrect       The correct answer is B: $currentAnswer2")
                }
                else if(correctAnswers[currentQuestion] == "D"){
                    speak(qiContext, "Incorrect       The correct answer is D: $currentAnswer4")
                }
                // set background color to red
                answer3.setBackgroundColor(Color.RED)
            }
            currentQuestion++
            if(currentQuestion == 5){
                val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
                var editor = sharedPreference.edit()
                var correctStr = correct.toString()
                editor.apply {
                    putString("score", "$correctStr")
                    apply()
                }
                val intent = Intent(this, RiseQuizResults::class.java)
                intent.putExtra("duration", duration)
                startActivity(intent)
            } else{
                // disable the answer buttons
                answer1.isEnabled = false
                answer2.isEnabled = false
                answer3.isEnabled = false
                answer4.isEnabled = false
                // set the next button to visible
                nextButton.isVisible = true
                nextButton.setOnClickListener {
                    question.setText(questions[currentQuestion])
                    answer1.setText(answers[currentQuestion*4])
                    answer2.setText(answers[currentQuestion*4 + 1])
                    answer3.setText(answers[currentQuestion*4 + 2])
                    answer4.setText(answers[currentQuestion*4 + 3])

                    currentQuestionStr = questions[currentQuestion]
                    currentAnswer1 = answers[currentQuestion*4]
                    currentAnswer2 = answers[currentQuestion*4 + 1]
                    currentAnswer3 = answers[currentQuestion*4 + 2]
                    currentAnswer4 = answers[currentQuestion*4 + 3]

                    speak(qiContext, "$currentQuestionStr                Is it: A $currentAnswer1            B $currentAnswer2            C $currentAnswer3            or D $currentAnswer4")

                    answer1.isEnabled = true
                    answer2.isEnabled = true
                    answer3.isEnabled = true
                    answer4.isEnabled = true
                    answer3.setBackgroundColor(Color.parseColor("#3F51B5"))
                    nextButton.isVisible = false
                }
            }
        }
        answer4.setOnClickListener {
            if(correctAnswers[currentQuestion] == "D"){
                correct++
                speak(qiContext, "Correct")
                // set background color to green
                answer4.setBackgroundColor(Color.GREEN)
            }
            else{
                if(correctAnswers[currentQuestion] == "A"){
                    speak(qiContext, "Incorrect       The correct answer is A: $currentAnswer1")
                }
                else if(correctAnswers[currentQuestion] == "B"){
                    speak(qiContext, "Incorrect       The correct answer is B: $currentAnswer2")
                }
                else if(correctAnswers[currentQuestion] == "C"){
                    speak(qiContext, "Incorrect       The correct answer is C: $currentAnswer3")
                }
                // set background color to red
                answer4.setBackgroundColor(Color.RED)
            }
            currentQuestion++
            if(currentQuestion == 5){
                val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
                var editor = sharedPreference.edit()
                var correctStr = correct.toString()
                editor.apply {
                    putString("score", "$correctStr")
                    apply()
                }
                val intent = Intent(this, RiseQuizResults::class.java)
                intent.putExtra("duration", duration)
                startActivity(intent)
            } else{
                // disable the answer buttons
                answer1.isEnabled = false
                answer2.isEnabled = false
                answer3.isEnabled = false
                answer4.isEnabled = false
                // set the next button to visible
                nextButton.isVisible = true
                nextButton.setOnClickListener {
                    question.setText(questions[currentQuestion])
                    answer1.setText(answers[currentQuestion*4])
                    answer2.setText(answers[currentQuestion*4 + 1])
                    answer3.setText(answers[currentQuestion*4 + 2])
                    answer4.setText(answers[currentQuestion*4 + 3])

                    currentQuestionStr = questions[currentQuestion]
                    currentAnswer1 = answers[currentQuestion*4]
                    currentAnswer2 = answers[currentQuestion*4 + 1]
                    currentAnswer3 = answers[currentQuestion*4 + 2]
                    currentAnswer4 = answers[currentQuestion*4 + 3]

                    speak(qiContext, "$currentQuestionStr                Is it: A $currentAnswer1            B $currentAnswer2            C $currentAnswer3            or D $currentAnswer4")

                    answer1.isEnabled = true
                    answer2.isEnabled = true
                    answer3.isEnabled = true
                    answer4.isEnabled = true
                    answer4.setBackgroundColor(Color.parseColor("#3F51B5"))
                    nextButton.isVisible = false
                }
            }
        }
    }

    fun speak(qiContext: QiContext, response: String) {
        val say: Future<Say> = SayBuilder.with(qiContext)
            .withText(
                "$response"
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