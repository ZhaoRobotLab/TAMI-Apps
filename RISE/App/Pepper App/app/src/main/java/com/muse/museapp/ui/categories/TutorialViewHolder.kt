/*
 * Copyright (C) 2018 Softbank Robotics Europe
 * See COPYING for the license
 */

package com.softbankrobotics.qisdktutorials.ui.categories

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.muse.museapp.R
import com.muse.museapp.model.data.Tutorial
import com.muse.museapp.model.data.TutorialLevel

import kotlinx.android.synthetic.main.tutorial_layout.view.*

/**
 * The view holder to show a tutorial.
 */
internal class TutorialViewHolder(itemView: View, private val onTutorialClickedListener: OnTutorialClickedListener) : RecyclerView.ViewHolder(itemView) {

    private var topicName: TextView = itemView.findViewById(R.id.topic_text)
    private var topicImage: ImageView = itemView.findViewById(R.id.topic_image)

    /**
     * Binds a tutorial to the views.
     * @param tutorial the tutorial to bind
     */
    fun bind(tutorial: Tutorial) {

        topicName.text = "${itemView.context.getString(tutorial.nameResId)}"
        tutorial.image?.let { topicImage.setImageResource(it) }

        itemView.button_localscence.setOnClickListener() {
            onTutorialClickedListener.onTutorialClicked(tutorial)
        }
//        with(itemView) {
//            isSelected = tutorial.isSelected
//            isEnabled = tutorial.isEnabled
//            setOnClickListener {
//                onTutorialClickedListener.onTutorialClicked(tutorial)
//                startActivity(Intent(this, CategoriesActivity::class.java))
//            }
//        }

        val tutorialLevel = tutorial.tutorialLevel
        bindLevelView(tutorialLevel)
    }


    /**
     * Bind the level view.
     * @param tutorialLevel the tutorial level
     */
    private fun bindLevelView(tutorialLevel: TutorialLevel) {
        when (tutorialLevel) {
            TutorialLevel.BASIC -> {
            }
        }
    }
}
