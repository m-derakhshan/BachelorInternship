package com.kharazmic.app.main.profile.setting.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kharazmic.app.R


class EditProfileViewModel(userInfo: UserInfoModel?) : ViewModel() {

    val name = MutableLiveData<String>()
    val image = MutableLiveData<String>()
    val experience = MutableLiveData<String>()
    val education = MutableLiveData<Int>()
    val worth = MutableLiveData<Int>()


    init {
        name.value = userInfo?.name
        image.value = userInfo?.image
        experience.value = userInfo?.experience?.let { "$it سال تجربه" }
        education.value = when (userInfo?.education) {
            1 -> R.id.phd
            2 -> R.id.master
            3 -> R.id.bachelor
            else -> R.id.less_education
        }
        worth.value = when (userInfo?.worth) {
            1 -> R.id.level1
            2 -> R.id.level2
            3 -> R.id.level3
            4 -> R.id.level4
            5 -> R.id.level5
            else -> R.id.level6
        }
    }

}