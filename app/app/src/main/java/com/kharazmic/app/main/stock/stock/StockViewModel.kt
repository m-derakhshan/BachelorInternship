package com.kharazmic.app.main.stock.stock


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StockViewModel() : ViewModel() {


    val volumeToday = MutableLiveData<String>()
    val volumeYesterday = MutableLiveData<String>()
    val volume10DaysAgo = MutableLiveData<String>()
    val volume1MonthAgo = MutableLiveData<String>()
    val volume3MonthAgo = MutableLiveData<String>()
    val fullName = MutableLiveData<String>()

    val chartBuySellInfo = MutableLiveData<ArrayList<StockBuySellChartModel>>()


    fun fetchData() {
        volumeToday.value = "۱۲۳۴۵۶۷"
        volumeYesterday.value = "۱۲۳۴۵۶۷"
        volume10DaysAgo.value = "۱۲۳۴۵۶۷"
        volume1MonthAgo.value = "۱۲۳۴۵۶۷"
        volume3MonthAgo.value = "۱۲۳۴۵۶۷"
        fullName.value = "فولاد مبارکه اصفهان"

        val info = ArrayList<StockBuySellChartModel>()
        for (i in 0..5) {
            info.add(
                StockBuySellChartModel(
                    sell_amount = "۹۹۹۹",
                    sell_price = "۱۰۰۰۰۰۰",
                    buy_price = "۱۰۰۰۰۰۰",
                    buy_amount = "۹۹۹۹",
                    buy_volume = "۱۲۳۴۵۶۷۸۹۰۱",
                    sell_volume = "۱۲۳۴۵۶۷۸۹۰۱"
                )
            )

        }
        chartBuySellInfo.value = info
    }


}