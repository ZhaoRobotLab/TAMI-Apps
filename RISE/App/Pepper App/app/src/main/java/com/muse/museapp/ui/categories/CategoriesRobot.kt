package com.muse.museapp.ui.categories

import android.util.Log
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.`object`.conversation.QiChatVariable
import com.aldebaran.qi.sdk.`object`.conversation.QiChatbot
import com.aldebaran.qi.sdk.`object`.conversation.TopicStatus
import com.aldebaran.qi.sdk.builder.*
import com.muse.museapp.R
import com.muse.museapp.model.data.Tutorial
import com.muse.museapp.model.data.TutorialCategory
import com.muse.museapp.model.data.TutorialLevel
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

private const val TAG = "CategoriesRobot"

private const val LEVEL_BASIC = "basic"



/**
 * The robot for the tutorial categories.
 */
internal class CategoriesRobot(private val presenter: CategoriesContract.Presenter) : CategoriesContract.Robot, RobotLifecycleCallbacks {
    private var moCATopicStatus: TopicStatus? = null
    private var qiChatbot: QiChatbot? = null
    private var chatFuture: Future<Void>? = null
    private var selectedCategory = TutorialCategory.Reminiscence
    //private var selectedLevel = TutorialLevel.BASIC
    private var levelVariable: QiChatVariable? = null
    private var isFirstIntro = true
    //used for robotIntroduction
    private var variable: QiChatVariable? = null
    private var pressed: String = ""


    override fun register(activity: CategoriesActivity) {
        QiSDK.register(activity, this)
    }

    override fun unregister(activity: CategoriesActivity) {
        QiSDK.unregister(activity, this)
    }

    override fun stopDiscussion(tutorial: Tutorial) {
        val chatFuture = chatFuture
        if (chatFuture != null) {
            chatFuture.thenConsume {
                if (it.isCancelled) {
                    presenter.goToTutorial(tutorial)
                }
            }
            chatFuture.requestCancellation()
        } else {
            presenter.goToTutorial(tutorial)
        }
        this.chatFuture = chatFuture
    }

    //This is the function where displaying the Robot introduction occurs
    override fun selectTopic(category: TutorialCategory) {
        selectedCategory = category

        //when selectTopic is called, display robotIntroduction
        variable?.async()?.setValue(pressed)

        val topicsAreReady = moCATopicStatus != null
        if (topicsAreReady) {
            enableTopic(category)
        }
    }

   /* override fun selectLevel(level: TutorialLevel) {
        selectedLevel = level

        if (levelVariable != null) {
            //enableLevel(level)
        }
    }*/

    override fun onRobotFocusGained(qiContext: QiContext) {
//        SayBuilder.with(qiContext)
//                .withText(qiContext.getString(introSentenceRes()))
//                .build()
//                .run()

        isFirstIntro = false

        val commonTopic = TopicBuilder.with(qiContext)
//            .withResource(R.raw.common)
            .build()

        val moCATopic = TopicBuilder.with(qiContext)
                .withResource(R.raw.greetings)
                .build()

        val qiChatbot = QiChatbotBuilder.with(qiContext)
                .withTopics(listOf(commonTopic, moCATopic))
                .build()
                .also { this.qiChatbot = it }

        val chat = ChatBuilder.with(qiContext)
                .withChatbot(qiChatbot)
                .build()

        //loops through Robot shoulders Animation recursively every 30 seconds
        loopAnimation(qiContext)



        //allows us to set variable value to pressed and call robot Introduction
        variable = qiChatbot.variable("pressed")
        pressed = "next"

        moCATopicStatus = qiChatbot.topicStatus(moCATopic)

        enableTopic(selectedCategory)

        qiChatbot.addOnBookmarkReachedListener {
            when (it.name) {
                "MoCA" -> {
                    visStatus = true
                    presenter.loadTutorials(TutorialCategory.Reminiscence)
                    selectTopic(TutorialCategory.Reminiscence)
                }
                "back" ->{
                    presenter.goBackSen()
                }
            }
        }

        qiChatbot.addOnEndedListener { presenter.goToTutorialForQiChatbotId(it) }
        this.qiChatbot = qiChatbot
        chatFuture = chat.async().run()
    }

    private var loopIndex: Int = 0
    private fun loopAnimation( qiContext: QiContext){


        // Create an executor that executes tasks in a background thread.
        val backgroundExecutor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()


        // Execute a task in the background thread after 30 seconds.
        backgroundExecutor.schedule({
            if(loopIndex != 0){
                val myAnimation: Animation = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.show_shoulders_a001)
                    .build()

                // Build the action.
                val animate: Animate = AnimateBuilder.with(qiContext)
                    .withAnimation(myAnimation)
                    .build()

                // Run the action asynchronously.
                animate.async().run()
            }
            // Your code logic goes here
            loopIndex++

            loopAnimation(qiContext)

        }, 20, TimeUnit.SECONDS)
    }

    override fun onRobotFocusLost() {
        qiChatbot?.let {
            it.removeAllOnBookmarkReachedListeners()
            it.removeAllOnEndedListeners()
            qiChatbot = null
        }
        chatFuture = null
        moCATopicStatus = null
    }

    override fun onRobotFocusRefused(reason: String) {
        Log.i(TAG, "onRobotFocusRefused: $reason")
    }

    /**
     * Enable the topic corresponding to the specified tutorial category.
     * @param category the tutorial category
     */
    private fun enableTopic(category: TutorialCategory) {
        val moCAFuture = moCATopicStatus?.async()?.setEnabled(false)

        Future.waitAll(moCAFuture)
                .andThenConsume {
                    when (category) {
                        TutorialCategory.Reminiscence -> moCATopicStatus?.enabled = true
                    }
                }
    }

    /**
     * Enable the specified level.
     * @param level the tutorial level
     */
    private fun enableLevel(level: TutorialLevel) {
        val value = levelValueFromLevel(level)
        levelVariable?.async()?.setValue(value)
    }

    /**
     * Provides the level variable value from the specified tutorial level.
     * @param level the tutorial level
     * @return The level variable value.
     */
    private fun levelValueFromLevel(level: TutorialLevel): String {
        return when (level) {
            TutorialLevel.BASIC -> LEVEL_BASIC
        }
    }
}
