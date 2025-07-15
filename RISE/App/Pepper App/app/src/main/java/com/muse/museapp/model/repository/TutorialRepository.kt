package com.muse.museapp.model.repository

import com.muse.museapp.R
import com.muse.museapp.model.data.Tutorial
import com.muse.museapp.model.data.TutorialCategory
import com.muse.museapp.model.data.TutorialId
import com.muse.museapp.model.data.TutorialLevel

class TutorialRepository {
    fun getTutorials(tutorialCategory: TutorialCategory, tutorialLevel: TutorialLevel): List<Tutorial> {
        return when (tutorialCategory) {
            TutorialCategory.Reminiscence -> getMoCATutorials(tutorialLevel)
        }
    }

    private fun getMoCATutorials(tutorialLevel: TutorialLevel): List<Tutorial> {
        val tutorials = mutableListOf<Tutorial>()

        when (tutorialLevel) {
            // ADD ACTIVITY: add an "add" call with these parameter - ActivityId (TutorialId.kt),
            TutorialLevel.BASIC -> {
//                Tutorial(val id: TutorialId, @param:StringRes @field:StringRes @get:StringRes val nameResId: Int,
//                val image: Int?, val qiChatbotId: String, val tutorialLevel: TutorialLevel
                //tutorials.add(Tutorial(TutorialId.RT_OpenAi, R.string.Elephant, R.mipmap.topic_pets,"OpenAi", TutorialLevel.BASIC))
            }
        }
        return tutorials
    }
}