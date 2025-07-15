package com.muse.museapp.activities

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.LayoutRes
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.muse.museapp.ui.categories.CategoriesActivity
import com.muse.museapp.ui.conversation.ConversationItemType
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.conversation_layout.*

var visStatusTopics: Boolean = false

abstract class TutorialActivity : RobotActivity() {

    private lateinit var rootView: View
    private var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    val defaultConvo = "Tammy: What is your favorite color?\n" +
            "User: My favorite color is blue.\n" +
            "Tammy: Wow! That is my favorite color too.\n" +
            "Tammy: What do you think of the picture?\n" +
            "User: I like it very much. It makes me feel happy.\n" +
            "Tammy: That's good, I am happy to know that you feel that way.\n"
    //val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.RECORD_AUDIO)
    //val REQUEST_CODE_PERMISSIONS = 1

    /**
     * Provide the tutorial layout identifier.
     * @return The layout identifier.
     */
    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        setupToolbar()
        visStatusTopics = true
        /*
        if (allPermissionsGranted()) {
            //
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, 1
            )
        }
         */
    }

    /*
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
     */

    override fun onResume() {
        super.onResume()

        rootView = findViewById(android.R.id.content)
        globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight.minus(rect.bottom)

            // Hide system UI if keyboard is closed.
            if (keypadHeight <= screenHeight * 0.30) {
                hideSystemUI()
            }
        }
        rootView.viewTreeObserver?.addOnGlobalLayoutListener(globalLayoutListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, CategoriesActivity::class.java))
    }

    override fun onPause() {
        rootView.viewTreeObserver?.removeOnGlobalLayoutListener(globalLayoutListener)
        super.onPause()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        close_button.setOnClickListener { finishAffinity() }
    }



    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    class Message internal constructor(pText: String, pSend: String) {
        var text = ""
        var sendBy = ""

        init {
            text = pText
            sendBy = pSend
        }
    }


    // This function enables python functions to be called in the activity. This needs to be called in OnStart
    fun pythonStart() {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
    }

    // This is a sample function to test that python can be used in the activity
    fun samplePythonFunction(): Int {
        val python: Python = Python.getInstance()
        val pythonFile: PyObject = python.getModule("sampleScript")
        return pythonFile.callAttr("samplePythonFunction").toInt()
    }

    // formats input
    fun addNextLine(original: String?, prompt: String?, ans: String?): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("myScript")
        return pythonFile.callAttr("addNextLine", original, prompt, ans).toString()
    }

    // takes an input and gets a response from Openai
    fun testResponse(prompt: String?): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("myScript")
        return pythonFile.callAttr("testResponse", prompt).toString()
    }

    fun removeQuestion(original: String?): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("myScript")
        return pythonFile.callAttr("removeQuestion", original).toString()
    }

    //add a conversation point
    fun addConvoLine(text: String, lineType: ConversationItemType){
        runOnUiThread{
        }
    }

    fun clearConvo() {
        runOnUiThread{
        }
    }
}