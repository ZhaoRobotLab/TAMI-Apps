package com.muse.museapp.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy
import com.muse.museapp.R
import com.muse.museapp.activities.GRA.GraHome
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Welcome : RobotActivity(), RobotLifecycleCallbacks {
/*
welcome activity, needs to lead into the trial runs
add lines explaining the trails to come
*/


    var mAudio: AudioManager? = null
    var press: Int = 0
    var subtitleView: TextView? = null

    //permission request vals
    val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val REQUEST_CODE_PERMISSIONS = 111

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE)

        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this)

        //check perms
        if (allPermissionsGranted()) {
            Log.i("Permissions", "granted!")
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        //set continue button function
        val continueButton = findViewById<Button>(R.id.continueButton)
        continueButton.setOnClickListener{
            val intent = Intent(this, GraHome::class.java)
            startActivity(intent)
        }

        //get subtitle to use
        val subtitle = findViewById<TextView>(R.id.subtitle)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode != REQUEST_CODE_PERMISSIONS){
            Log.d("requestPerms", "Perms not granted")
            finish()
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        fun makeSay(text: String){
            lifecycleScope.launch(Dispatchers.Main){
                subtitle?.text = text
            }.invokeOnCompletion {
                lifecycleScope.launch(Dispatchers.Default) {
                    val phrase = Phrase("\\rspd=77\\ \\vct=72\\ $text");
                    val say = SayBuilder.with(qiContext)
                        .withPhrase(phrase)
                        .build()

                    say.async().run()
                }
            }
        }

        val volumeCont = findViewById<SeekBar>(R.id.VolumeControl)
        val changeVolText = findViewById<TextView>(R.id.VolumeTextView)
        mAudio = getSystemService(Context.AUDIO_SERVICE) as AudioManager?

        volumeCont.max = mAudio?.getStreamMaxVolume(AudioManager.STREAM_MUSIC)!!
        mAudio?.setStreamVolume(AudioManager.STREAM_MUSIC, 7 , 0)

        makeSay("Welcome to your Cancer Genetic Education! Please use the volume" +
                "bar to select your desired volume level.")

//        runOnUiThread {
            volumeCont.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    Log.i("settings", "setting volume$progress")
                    //mAudio?.setStreamVolume(AudioManager.STREAM_SYSTEM, progress, AudioManager.FLAG_PLAY_SOUND)
                    mAudio?.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 4)
                }

                override fun onStartTrackingTouch(seek: SeekBar) {
                    // write custom code for progress is started
                }

                override fun onStopTrackingTouch(seek: SeekBar) {
                    // write custom code for progress is stopped
                    makeSay("Hello,  my name is Tammy ")
                }
            })
//        }
    }

    override fun onRobotFocusLost() {
        Log.i("status", "Focus Lost, popping fragment")
        //pop the repeatbar
        this.supportFragmentManager.popBackStack()
    }

    override fun onRobotFocusRefused(reason: String?) {

    }


}