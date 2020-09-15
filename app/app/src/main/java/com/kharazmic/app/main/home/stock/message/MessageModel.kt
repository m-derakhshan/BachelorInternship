package com.kharazmic.app.main.home.stock.message

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MessageModel(
    val id: String,
    val symbol: String,
    val name: String,
    val date: String,
    val time: String,
    val message: String
) : Parcelable