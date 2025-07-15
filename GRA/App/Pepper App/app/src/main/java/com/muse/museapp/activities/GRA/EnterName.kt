package com.muse.museapp.activities.GRA

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.muse.museapp.R
import com.muse.museapp.activities.Welcome
import java.util.*

class EnterName : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var listenButton: Button
    private lateinit var repeatButton: Button
    private lateinit var mainMenuButton: Button
    private lateinit var nameEditText: EditText
    private var listofchats = ""
    private val RQ_SPEECH_REC = 102
    private var duration = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContentView(R.layout.name_entry)

        // Initialize UI elements
        nameEditText = findViewById<EditText>(R.id.editTextName)
        listenButton = findViewById<Button>(R.id.listenButton2)
        listenButton.setOnClickListener {
            askSpeechInput()
        }
        setButtons()
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    fun setButtons() {
        nextButton = findViewById<Button>(R.id.seat_reminder1_next)
        nextButton.setOnClickListener {
            UserData.userName = nameEditText.text.toString()
            val intent = Intent(this, GraSlides::class.java)
            startActivity(intent)
        }

        backButton = findViewById<Button>(R.id.seat_reminder1_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        mainMenuButton = findViewById<Button>(R.id.seat_reminder1_mainmenu)
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
            val recognizedText = result?.get(0).toString()
            if (recognizedText.isNotEmpty()) {
                nameEditText.setText(recognizedText)
                UserData.userName = recognizedText  // Store the name globally
            } else {
                Toast.makeText(this, "Could not recognize any speech.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Speech recognition canceled or failed.", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onRobotFocusGained(qiContext: QiContext) {
        repeatButton = findViewById<Button>(R.id.seat_reminder1_repeat)
        repeatButton.setOnClickListener {
            speak(qiContext)
        }
        // No need to use runOnUiThread here for non-UI operations
        speak(qiContext) // Call speak outside runOnUiThread
    }
//    fun speak(qiContext: QiContext) {
//
//        val speech: Say = SayBuilder.with(qiContext)
//            .withText("Before we begin, please introduce me yourself!       Type in your name by clicking in the empty box    ")
//            .build()
//        speech.run()
//    }

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

    fun speak(qiContext: QiContext) {
        val response = "Before we begin, please introduce yourself!     Type in your name by clicking in the empty box, or click on the listen button to speak your name into the microphone "
        val phrase = Phrase("\\rspd=77\\ \\vct=72\\ $response")
        val sayFuture: Future<Say> = SayBuilder.with(qiContext)
            .withPhrase(phrase)
            .buildAsync()
        sayFuture.andThenConsume { say ->
            say.async().run()
        }
    }


    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
    }

    override fun onRobotFocusRefused(reason: String?) {
        TODO("Not yet implemented")
    }
}