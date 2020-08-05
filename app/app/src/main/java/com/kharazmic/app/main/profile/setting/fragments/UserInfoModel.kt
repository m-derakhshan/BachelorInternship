package com.kharazmic.app.main.profile.setting.fragments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfoModel(
    val name: String,
    val image: String,
    val worth: Int,
    val experience: Int,
    val education: Int,
    val maxDays: Float,
    val remainingDays: Float,
    val subscription: String,
    val followers: String,
    val following: String,
    val signals: String
) : Parcelable