package com.kharazmic.app.main.home.digital_currency

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DigitalCurrencyModel(
    val id: String = "",
    val name: String = "",
    val price_dollar: String = "",
    val price_dollar_change_24: String = "",
    val price_dollar_change_3: String = "",
    val price_dollar_change_7_days: String = "",
    val price_rial: String = "",
    val price_rial_change_24: String = "",
    val icon: String = "",
    val update: String = "",
    val value_last_24_hours: String = "",
    val total_value: String = ""
) : Parcelable