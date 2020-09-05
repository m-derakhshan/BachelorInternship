package com.kharazmic.app.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            return modelClass.getConstructor(Context::class.java).newInstance(context)
        throw  IllegalArgumentException("model class was not recognized")
    }
}