package com.kharazmic.app.main.gold

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GoldCurrencyModel(
    val title: String,
    val price: String,
    val changePercentage: Int,
    val date: String
) : Parcelable