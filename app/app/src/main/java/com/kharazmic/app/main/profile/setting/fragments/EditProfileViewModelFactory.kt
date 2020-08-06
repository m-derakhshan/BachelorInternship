package com.kharazmic.app.main.profile.setting.fragments

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.database.UserInfoModel

class EditProfileViewModelFactory(private val database: MyDatabase, private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java))
            return EditProfileViewModel(database = database, context = context) as T
        throw IllegalArgumentException("model class is not true")
    }

}