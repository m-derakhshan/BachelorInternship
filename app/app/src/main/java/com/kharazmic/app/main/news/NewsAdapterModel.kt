package com.kharazmic.app.main.news

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class NewsAdapterModel(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val source: String,
    val image: String,
    val tags: ArrayList<String>? = null
) : Parcelable