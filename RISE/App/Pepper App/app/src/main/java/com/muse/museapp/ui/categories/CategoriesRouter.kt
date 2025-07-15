//
//package com.muse.museapp.ui.categories
//
//import android.content.Context
//import android.content.Intent
//import android.util.Log
////import com.muse.museapp.activities.Cars.carremsession
////import com.muse.museapp.activities.Chores.choressession
////import com.muse.museapp.activities.Holidays.holidayssession
////import com.muse.museapp.activities.Movies.moviessession
//import com.muse.museapp.activities.Music.musicsession
//import com.muse.museapp.activities.Rem_Animal.animalsession
//import com.muse.museapp.activities.Rem_LS.localsession
//import com.muse.museapp.activities.Sports.sportssession
//import com.muse.museapp.activities.TutorialActivity
//import com.muse.museapp.model.data.Tutorial
//import com.muse.museapp.model.data.TutorialId
//import com.muse.museapp.utils.Constants
//
//
//private const val TAG = "CategoriesRouter"
//
///**
// * The router for the tutorial categories.
// */
//internal class CategoriesRouter : CategoriesContract.Router {
//
//    override fun goToTutorial(tutorial: Tutorial, context: Context) {
//        val tutorialId = tutorial.id
//        val intent = Intent(context, getDestinationActivity(tutorialId))
//        intent.putExtra(Constants.Intent.TUTORIAL_NAME_KEY, tutorial.nameResId)
//        intent.putExtra(Constants.Intent.TUTORIAL_LEVEL_KEY, tutorial.tutorialLevel)
//        context.startActivity(intent)
//    }
//
//    /**
//     * Provide the destination activity class for the specified tutorial identifier.
//     * @param tutorialId the tutorial identifier
//     * @return The destination activity class.
//     */
////    private fun getDestinationActivity(tutorialId: TutorialId): Class<out TutorialActivity> {
////        val i = Log.i(TAG, "tutorialId: $tutorialId")
//////        return when (tutorialId) {
////////            TutorialId.Introduction -> Introsession::class.java
//////            TutorialId.RT_Animals -> animalsession::class.java
//////            TutorialId.RT_LocalScence -> localsession::class.java
//////            TutorialId.RT_Cars -> carremsession::class.java
//////            TutorialId.RT_Hobbies -> hobbiessession::class.java
//////            TutorialId.RT_Holidays -> holidayssession::class.java
//////            TutorialId.RT_Movies -> moviessession::class.java
//////            TutorialId.RT_Music -> musicsession::class.java
//////            TutorialId.RT_Sports -> sportssession::class.java
//////            TutorialId.RT_Chores -> choressession::class.java
//////            //TutorialId.RT_OpenAi -> testopenaisession::class.java
//////
//////        }
////    }
//}
