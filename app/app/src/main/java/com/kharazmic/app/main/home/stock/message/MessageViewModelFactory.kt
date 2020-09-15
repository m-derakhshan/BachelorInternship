package com.kharazmic.app.main.home.stock.message

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MessageViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessageViewModel::class.java))
            return modelClass.getConstructor(Context::class.java).newInstance(context)
        throw IllegalArgumentException("model class is not true")
    }
}