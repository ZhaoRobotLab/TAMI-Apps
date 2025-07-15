package com.muse.museapp.activities.GRA

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.ProgressBar
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.muse.museapp.R
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.muse.museapp.activities.Welcome
import java.util.*

class GraQnA : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var repeatButton: Button
    private lateinit var submitButton: Button
    private lateinit var mainMenuButton: Button
    private lateinit var listenButton: Button
    private lateinit var chats: EditText
    private lateinit var text: TextView
    private lateinit var loadingPanel: ProgressBar
    private lateinit var costInfoTextView: TextView
    private var listofchats = ""
    private val RQ_SPEECH_REC = 102
    private var duration = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.gra_qna)

        // Adding the cost info message to the center of the screen, it disappears once the user taps on the it.
        // costInfoTextView = findViewById(R.id.costInfoTextView)
//        costInfoTextView.text = "Your provider will discuss specifics regarding potential out of pocket costs and insurance coverage with you before any testing is done. Please ask your provider any questions regarding costs of testing at the conclusion of your genetic risk assessment education session."
//        costInfoTextView.setOnClickListener {
//            costInfoTextView.visibility = View.GONE
//        }

        text = findViewById<TextView>(R.id.textView_hello)
        text.setText("Hello! Welcome to the QnA session! Ask me anything. ")

        loadingPanel = findViewById(R.id.loadingPanel)

        pythonStart()
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

    fun pythonStart() {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
    }

    private fun getResponse(query: String): String{
        val python = Python.getInstance()
        val pythonFile = python.getModule("queryscript")
        return pythonFile.callAttr("response", query).toString()
    }

    fun setButtons() {
        nextButton = findViewById<Button>(R.id.rhythm_transition2_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, GraQuiz::class.java)
            intent.putExtra("duration", duration)
            startActivity(intent)
        }

        mainMenuButton = findViewById<Button>(R.id.rhythm_transition2_mainmenu)
        mainMenuButton.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RQ_SPEECH_REC && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            chats.setText(result?.get(0).toString())
        }
    }

    private fun askSpeechInput() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech recognition is not available", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")
            startActivityForResult(i, RQ_SPEECH_REC)
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        repeatButton = findViewById<Button>(R.id.rhythm_transition2_repeat)
        repeatButton.setOnClickListener {
            speak(qiContext, "This is a Q and A session!          Please ask if you have any questions.      ")
        }
        runOnUiThread{
            chats = findViewById<EditText>(R.id.editText_chatInput)
            submitButton = findViewById<Button>(R.id.rhythm_transition2_submit)
            submitButton.setOnClickListener {
                loadingPanel.visibility = View.VISIBLE
                submitButton.setBackgroundColor(Color.GREEN)
                val chat = chats.text.toString()

                Thread {
                    val response = getResponse(chat)

                    runOnUiThread{
                        listofchats += "\nUser: $chat\n\nAI: $response\n"
                        chats.setText("")
                        text.setText(listofchats)
                        submitButton.setBackgroundColor(Color.parseColor("#3F51B5"))
                        loadingPanel.visibility = View.GONE
                        speak(qiContext, response)
                    }
                }.start()
            }

            listenButton = findViewById<Button>(R.id.listenButton)
            listenButton.setOnClickListener{
                askSpeechInput()
            }
        }
        speak(qiContext, "Here is an opportunity to ask any questions you have on your mind.          Your provider will discuss specifics regarding potential out of pocket costs and insurance coverage with you before any testing is done.          Please ask your provider instead of me, any questions regarding costs of testing at the conclusion of your genetic education session.            Please ask if you have any questions.     ")
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
        // TODO("Not yet implemented")
    }

    override fun onRobotFocusRefused(reason: String?) {
        // TODO("Not yet implemented")
    }
}
