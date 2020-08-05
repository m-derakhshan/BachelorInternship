package com.kharazmic.app.main.profile.setting.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditProfileViewModelFactory(private val infoModel: UserInfoModel?) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java))
            return EditProfileViewModel(infoModel) as T
        throw IllegalArgumentException("model class is not true")
    }

}