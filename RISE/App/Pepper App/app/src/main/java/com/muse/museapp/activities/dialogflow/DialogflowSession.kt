package com.muse.museapp.activities.dialogflow

import android.os.Bundle
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Chat
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.ChatBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.muse.museapp.R
import com.muse.museapp.activities.TutorialActivity
import com.muse.museapp.ui.conversation.ConversationBinder
import kotlinx.android.synthetic.main.conversation_layout.*

class DialogflowSession : TutorialActivity(), RobotLifecycleCallbacks {
    private lateinit var conversationBinder: ConversationBinder
    private lateinit var chat : Chat

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
    override val layoutId = R.layout.conversation_layout

    override fun onRobotFocusGained(qiContext: QiContext) {
        val conversationStatus = qiContext.conversation.status(qiContext.robotContext)
        // Create a new say action.
        val say: Say = SayBuilder.with(qiContext) // Create the builder with the context.
            .withText("Hello human!") // Set the text to say.
            .build() // Build the say action.

        // Execute the action.
        say.run()
        val credentials = applicationContext.resources.openRawResource(R.raw.joke_credentials)
        val dialogflowChatbot = DialogflowChatbot(qiContext, credentials)
        chat = ChatBuilder.with(qiContext).withChatbot(dialogflowChatbot).build()
        chat.async().run()
    }

    override fun onRobotFocusLost() {
        // The robot focus is lost.
        conversationBinder.unbind()
    }

    override fun onRobotFocusRefused(reason: String) {
        // The robot focus is refused.
    }


}