package com.muse.museapp.ui.conversation

import android.text.TextUtils
import com.aldebaran.qi.sdk.`object`.conversation.ConversationStatus

class ConversationBinder private constructor(private val conversationStatus: ConversationStatus) {

    private var onSayingChangedListener: ConversationStatus.OnSayingChangedListener? = null
    private var onHeardListener: ConversationStatus.OnHeardListener? = null


    fun unbind() {
        conversationStatus.removeOnSayingChangedListener(onSayingChangedListener)
        conversationStatus.removeOnHeardListener(onHeardListener)
        onSayingChangedListener = null
        onHeardListener = null
    }

    companion object {

    }

    fun filter(string: String) : String {
        var s : String = ""
        val s1 : String = string.replace("\\", "|")
        var bool : Boolean = true
        if(string.contains("(skip)")) return "Saying..."
        for (char: Char in s1) {
            if (char == '|' && bool) {
                bool = false
            }else if(char == '|' && !bool){
                bool = true
            }else if(bool){
                s += char
            }
        }
        return s
    }
}