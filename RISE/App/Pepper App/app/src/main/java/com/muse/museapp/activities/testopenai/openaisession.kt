package com.muse.museapp.activities.testopenai

import android.os.Bundle
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.muse.museapp.R
import com.muse.museapp.activities.TutorialActivity

class testopenaisession : TutorialActivity(), RobotLifecycleCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this)
    }

    override fun onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        pythonStart()
        //bindService(Intent(this, SpeechService::class.java), mServiceConnection, BIND_AUTO_CREATE) //
    }

    override val layoutId = R.layout.layout_info_log_view

    override fun onRobotFocusGained(qiContext: QiContext) {

        val say = SayBuilder.with(qiContext) // Create the builder with the context.
            .withText("Hello human!") // Set the text to say.
            .build() // Build the say action.

        // Execute the action.
        say.run()
    }

    override fun onRobotFocusLost() {
    }

    override fun onRobotFocusRefused(reason: String) {
        // Nothing here.
    }

    /*
    // formats input
    private fun fixInput(original: String?, prompt: String?, ans: String?): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("myScript")
        return pythonFile.callAttr("addNextLine", original, prompt, ans).toString()
    }

    // takes an input and gets a response from Openai
    private fun getString(prompt: String?): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("myScript")
        return pythonFile.callAttr("testResponse", prompt).toString()
    }

    private fun pythonStart() {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
    }

     */

}