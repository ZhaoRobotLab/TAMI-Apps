package com.muse.museapp.ui.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.muse.museapp.R
import kotlinx.android.synthetic.main.activity_toolbar.view.*

class ActivityToolBar (context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : Toolbar(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.activity_toolbar, this)
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs,0)

    override fun setNavigationOnClickListener(listener: OnClickListener) {
        back_arrow.setOnClickListener(listener)
    }



}