/*
 * Copyright (C) 2018 Softbank Robotics Europe
 * See COPYING for the license
 */

package com.muse.museapp.ui.categories

import android.os.Handler
import android.os.Looper
import com.muse.museapp.model.data.Tutorial
import com.muse.museapp.model.data.TutorialCategory
import com.muse.museapp.model.data.TutorialLevel
import com.muse.museapp.model.repository.TutorialRepository


/**
 * The presenter for the tutorial categories.
 */
internal class CategoriesPresenter : CategoriesContract.Presenter {
    private var view: CategoriesContract.View? = null
    private val tutorialRepository: TutorialRepository = TutorialRepository()
    private var loadedTutorials = listOf<Tutorial>()
    private var selectedCategory = TutorialCategory.Reminiscence
    private var selectedLevel = TutorialLevel.BASIC

    override fun bind(view: CategoriesContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun loadTutorials(category: TutorialCategory) {
        selectedCategory = category
        updateTutorials()
    }

    override fun loadTutorials(level: TutorialLevel) {
        selectedLevel = level
        updateTutorials()
    }

    override fun goToTutorialForQiChatbotId(tutorialQiChatbotId: String) {
        for (tutorial in loadedTutorials) {
            if (tutorial.qiChatbotId == tutorialQiChatbotId) {
                view?.selectTutorial(tutorial)
                view?.goToTutorial(tutorial)
                break
            }
        }
    }

    override fun goToTutorial(tutorial: Tutorial) {
        view?.goToTutorial(tutorial)
    }

    private fun updateTutorials() {
        loadedTutorials = tutorialRepository.getTutorials(selectedCategory, selectedLevel)
        view?.showTutorials(loadedTutorials)
        Handler(Looper.getMainLooper()).postDelayed({
            if(visStatus) view?.hideMenu()
        }, 20)
    }

    override fun goBackSen(){
        Handler(Looper.getMainLooper()).postDelayed({
            view?.showMenu()
        }, 20)
    }
}
