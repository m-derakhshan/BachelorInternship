package com.kharazmic.app.main.home.gold

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GoldCurrencyModel(
    val title: String,
    val price: String,
    val changePercentage: Double,
    val date: String
) : Parcelable