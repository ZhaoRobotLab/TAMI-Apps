package com.muse.museapp.activities.dialogflow

import android.util.Log
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.BaseChatbotReaction
import com.aldebaran.qi.sdk.`object`.conversation.SpeechEngine
import com.aldebaran.qi.sdk.builder.SayBuilder
import java.util.concurrent.ExecutionException
import com.aldebaran.qi.Future

class SimpleSayReaction internal constructor(context: QiContext, private val answer: String) :
    BaseChatbotReaction(context) {
    private var sayFuture: Future<Void>? = null

    override fun runWith(speechEngine: SpeechEngine) {
        val say = SayBuilder.with(speechEngine).withText(answer).build()
        sayFuture = say.async().run()
        try {
            sayFuture?.get() // Block until action is done
        } catch (e: ExecutionException) {
            Log.e("SimpleSayReaction", "Error during say: $e")
        }
    }

    override fun stop() {
        sayFuture?.requestCancellation()
    }
}