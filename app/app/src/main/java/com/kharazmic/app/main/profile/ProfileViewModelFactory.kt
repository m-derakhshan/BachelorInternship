package com.kharazmic.app.main.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kharazmic.app.database.MyDatabase


class ProfileViewModelFactory(val context: Context, val database: MyDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java))
            return modelClass.getConstructor(Context::class.java, MyDatabase::class.java)
                .newInstance(context, database)
        throw IllegalArgumentException("model class is unknown!")
    }

}