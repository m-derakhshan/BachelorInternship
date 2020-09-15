package com.kharazmic.app.main.home.desk.constant

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ConstantIncomeModel(
    val id: String = "",
    val name: String = "",
    val averageProfit: String = "",
    val riskCriteria: String = "",
    val manager: String = "",
    val guarantor: String = "",
    val worth_amount: String = "",
    val supervisor: String = "",
    val type: String = "",
    val start_date: String = "",
    val accounting: String = "",
    val address: String = "",
    val update_date: String = "",
    val price_per_unit: String = "",
    val amount_invest_unit: String = "",
    val net_worth: String = "",
    val cancel_price: String = "",
    val number_of_investor2: String = "",
    val percentage_investor2: String = "",
    val statistic_price_per_unit: String = "",
    val stock: String = "",
    val bank: String = "",
    val cash: String = "",
    val other: String = "",
    val shared: String = "",
    val most_weight: String = "",
    val guaranteed_profit: String = "",
    val one_month: String = "",
    val predicted: String = "",
    val three_month: String = "",
    val divide: String = "",
    val six_month: String = "",
    val annual: String = "",
    val total_profit: String = "",
    val number_investor1: String = "",
    val percentage_investor1: String = ""
) : Parcelable