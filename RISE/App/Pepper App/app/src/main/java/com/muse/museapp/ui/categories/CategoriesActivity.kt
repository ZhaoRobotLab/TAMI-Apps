package com.muse.museapp.ui.categories

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.softbankrobotics.qisdktutorials.ui.categories.OnTutorialClickedListener
import com.softbankrobotics.qisdktutorials.ui.categories.TutorialAdapter
import com.muse.museapp.R
import com.muse.museapp.activities.visStatusTopics
import com.muse.museapp.model.data.Tutorial
import com.muse.museapp.model.data.TutorialCategory
import com.muse.museapp.utils.GoogleTTS
import kotlinx.android.synthetic.main.activity_categories.*
import com.muse.museapp.activities.RISE.RiseHome

var visStatus: Boolean = false

class CategoriesActivity : RobotActivity(), CategoriesContract.View, OnTutorialClickedListener {
    private var googleTTS: GoogleTTS? = null

    private lateinit var presenter: CategoriesContract.Presenter
    private lateinit var robot: CategoriesContract.Robot
    private lateinit var router: CategoriesContract.Router

    private lateinit var tutorialAdapter: TutorialAdapter

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val REQUEST_CODE_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        visStatus = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        //check perms
        if (allPermissionsGranted()) {
//            googleTTS?.bindToActivity(this)
        }
        else{
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, 1
            )
        }

        setupButtons()
        setupRecyclerView()

        val presenter = CategoriesPresenter()
        robot = CategoriesRobot(presenter)
       // router = CategoriesRouter()

        presenter.bind(this)
        robot.register(this)


        presenter.loadTutorials(TutorialCategory.Reminiscence)
        this.presenter = presenter
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
//            googleTTS?.bindToActivity(this)
        }
        else{
            Log.d("requestPerms", "Perms not granted")
//            finish()
        }
    }


    override fun onResume() {
        super.onResume()
        //tutorialAdapter.unselectTutorials()
        //tutorialAdapter.setTutorialsEnabled(true)
        visStatus = false
        if(visStatusTopics){
            visStatus = true
        }

//        googleTTS?.bindToActivity(this)
    }

    override fun onDestroy() {
        robot.unregister(this)
        presenter.unbind()
        super.onDestroy()
    }


    override fun onBackPressed() {
       // super.onBackPressed()
        //startActivity(Intent(this, CategoriesActivity::class.java)) reset the activity
        hideMenu()
    }

    override fun showTutorials(tutorials: List<Tutorial>) {
        runOnUiThread { tutorialAdapter.updateTutorials(tutorials) }
    }

    override fun selectTutorial(tutorial: Tutorial) {
        runOnUiThread {
            tutorialAdapter.selectTutorial(tutorial)
            tutorialAdapter.setTutorialsEnabled(false)
        }
    }

    override fun goToTutorial(tutorial: Tutorial) {
        runOnUiThread { router.goToTutorial(tutorial, this@CategoriesActivity) }
    }

    override fun onTutorialClicked(tutorial: Tutorial) {
        tutorialAdapter.selectTutorial(tutorial)
        tutorialAdapter.setTutorialsEnabled(false)
        robot.stopDiscussion(tutorial)
    }

    /**
     * Configure the buttons.
     */
    private fun setupButtons() {
//        mindfulness_button.setOnClickListener {
//            visStatus = true
//            presenter.loadTutorials(TutorialCategory.Reminiscence)
//            robot.selectTopic(TutorialCategory.Reminiscence)
//        }
        muse_button.setOnClickListener {
            //visStatus = true
            //presenter.loadTutorials(TutorialCategory.Muse)
            //robot.selectTopic(TutorialCategory.Muse)
            //setContentView(R.layout.muse_home)
            val intent = Intent(this, RiseHome::class.java)
            startActivity(intent)
        }

        /*val audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val upButton = findViewById<View>(R.id.upButton) as Button
        upButton.setOnClickListener{
            fun onClick(v: View?) {

                //To increase media player volume
                audioManager.adjustWelcomeVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
            }
        }

        val downButton = findViewById<View>(R.id.downButton) as Button
        downButton.setOnClickListener {
            fun onClick(v: View?) {

                //To decrease media player volume
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
            }
        }*/

    }

    /**
     * Configure the recycler view.
     */
    private fun setupRecyclerView() {
        tutorialAdapter = TutorialAdapter(this)
        recycler_view.layoutManager = GridLayoutManager(this, 3)
        recycler_view.adapter = tutorialAdapter

        val drawable = getDrawable(R.drawable.empty_divider_tutorials)
        if (drawable != null) {
            val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(drawable)
            recycler_view.addItemDecoration(dividerItemDecoration)
        }
    }

    override fun hideMenu() {
        gLayout.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
    }

    override fun showMenu() {
        gLayout.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
    }

}