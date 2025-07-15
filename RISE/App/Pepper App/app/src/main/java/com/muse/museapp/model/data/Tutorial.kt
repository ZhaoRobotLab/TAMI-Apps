package com.muse.museapp.model.data

import androidx.annotation.StringRes

data class Tutorial(
    val id: TutorialId, @param:StringRes @field:StringRes @get:StringRes val nameResId: Int, val image: Int?, val qiChatbotId: String, val tutorialLevel: TutorialLevel) {
    var isSelected: Boolean = false
    var isEnabled: Boolean = true
}