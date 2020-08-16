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
    val chartLegalRealInfo = MutableLiveData<ArrayList<StockLegalRealChartModel>>()


    fun fetchData() {
        volumeToday.value = "۱۲۳۴۵۶۷"
        volumeYesterday.value = "۱۲۳۴۵۶۷"
        volume10DaysAgo.value = "۱۲۳۴۵۶۷"
        volume1MonthAgo.value = "۱۲۳۴۵۶۷"
        volume3MonthAgo.value = "۱۲۳۴۵۶۷"
        fullName.value = "فولاد مبارکه اصفهان"

        val info = ArrayList<StockBuySellChartModel>()
        for (i in 0..4) {
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


        val legalInfo = ArrayList<StockLegalRealChartModel>()
        val subject = listOf("حجم خرید", "حجم فروش", "تعداد خریدار", "تعداد فروشنده")
        for (subject in subject) {
            legalInfo.add(
                StockLegalRealChartModel(
                    legal = "۱۲۳۴۵۶۷۸۹۰",
                    title = subject,
                    real = "۱۲۳۴۵"
                )
            )
        }
        chartLegalRealInfo.value = legalInfo
    }


}