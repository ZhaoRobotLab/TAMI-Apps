package com.muse.museapp.activities.Introduction

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.BookmarkStatus
import com.aldebaran.qi.sdk.`object`.conversation.Chat
import com.aldebaran.qi.sdk.`object`.conversation.QiChatbot
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.muse.museapp.R
import com.muse.museapp.activities.TutorialActivity
import com.muse.museapp.ui.conversation.ConversationBinder
import kotlinx.android.synthetic.main.conversation_layout.*
//import kotlinx.android.synthetic.main.naming_layout.*

class Introsession : TutorialActivity(), RobotLifecycleCallbacks{
    private lateinit var conversationBinder: ConversationBinder
    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.RECORD_AUDIO)
    val REQUEST_CODE_PERMISSIONS = 1
    private lateinit var qiChatbot: QiChatbot
    private lateinit var chat: Chat
    private lateinit var fchat : Future<Void>
    private lateinit var countBookmarkStatus: BookmarkStatus
    private val randomNumber = mutableListOf<Int>()
    private val aniNameList : List<List<String>> = listOf(listOf("dog", "puppy"), listOf("cat", "kitten"), listOf("bear"), listOf("crocodile"), listOf("elephant"), listOf("horse"), listOf("kangaroos"), listOf("parrot"), listOf("shark"), listOf("snake"), listOf("squid"), listOf("donkey"))
//    private val aniPicList : List<Int> = listOf(R.mipmap.dog, R.mipmap.cat, R.mipmap.bear, R.mipmap.crocodile, R.mipmap.elephant, R.mipmap.horse, R.mipmap.kangaroos, R.mipmap.parrot, R.mipmap.shark, R.mipmap.snake, R.mipmap.squid, R.mipmap.donkey)

//    private val picList : List<Int> = listOf(R.mipmap.emob, R.mipmap.sewing, R.mipmap.dancing, R.mipmap.game)

    private var count: Int = 0;
    private var edgeCase: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        if (allPermissionsGranted()) {
            //
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, 1
            )
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS){
            //setupRecognition()
        }
        else{
            Log.d("requestPerms", "Perms not granted")
            finish()
        }
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override val layoutId = R.layout.conversation_layout

    override fun onRobotFocusGained(qiContext: QiContext) {
        val conversationStatus = qiContext.conversation.status(qiContext.robotContext)

        val say: Say = SayBuilder.with(qiContext)
            .withText("Hello human! Welcome to the Reminiscence task! When you are ready, go back to the previous page" +
                    " and pick a topic. You will be shown images along with questions. If you wish to skip any of the" +
                    " images, press the skip button. When you are ready to move on to another image, say 'next'.")
            .build()

        say.run()

        val say2: Say = SayBuilder.with(qiContext)
            .withText("When you are ready, click the back arrow at the top of this page and choose a topic to begin.")
            .build()

        say2.run()
    }

    override fun onRobotFocusLost() {
    }

    override fun onRobotFocusRefused(reason: String) {
        // Nothing here.
    }



}