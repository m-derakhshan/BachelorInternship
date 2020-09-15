package com.kharazmic.app.main.home.digital_currency

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DigitalCurrencyModel(
    val id: String = "",
    val english_name: String = "",
    val persian_name: String = "",
    val price_dollar: String = "",
    val price_dollar_change_24: Double = 0.0,
    val price_dollar_change_3: Double = 0.0,
    val price_dollar_change_7_days: Double = 0.0,
    val price_rial: String = "",
    val price_rial_change_24: Double = 0.0,
    val icon: String = "",
    val update: String = "",
    val value_last_24_hours: String = "",
    val total_value: String = ""
) : Parcelable