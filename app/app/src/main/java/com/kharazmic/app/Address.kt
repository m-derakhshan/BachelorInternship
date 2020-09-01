package com.kharazmic.app

class Address {

    private val base = "http://95.216.62.137:1337"


    fun loginAPI(phone: String) = "$base/people/genpassword?mobile=$phone"


    val validatePhoneAPI = "$base/auth/local"

    fun stockSearch(keyword: String, page: String): String =
        "https://run.mocky.io/v3/a16de51a-5b19-4110-afa9-487e1606c22d?keyword=$keyword&page=$page"


    val updateInfoAPI = "https://run.mocky.io/v3/8763b1c1-2bec-4b35-badc-7d9adc753df7"

    val userInfoAPI = "https://run.mocky.io/v3/d9dfadd5-c256-4846-8e69-a4ebdd64173d"


    val ticketAPI = "https://run.mocky.io/v3/082ecf40-a6f3-44ef-a9f3-1568d2f299a8"

    val signalDetailAPI = "https://run.mocky.io/v3/fb70bab7-217b-4e3f-a7b8-8d2955834678"

    val bestStockAPI = "https://run.mocky.io/v3/6964016e-e4c4-4d5b-90de-e3651da077b7"

    val gold = "https://run.mocky.io/v3/6530e07a-c219-4fb8-a0b4-5881e15cde16"


    fun allBestStockAPI(page: String, category: String?): String =
        "https://run.mocky.io/v3/e69b867d-25c6-4b46-80c0-61d228f4e05f"

    fun newsAPI(keyword: String, category: String, page: Int): String =
        "$base/news/" +
                "category/$category/" + "keyword/$keyword/" + "page/$page"

    fun tutorialAPI(keyword: String, category: String, page: Int): String =
        "$base/tutorials/" +
                "category/$category/" + "keyword/$keyword/" + "page/$page"

    fun signalsAPI(category: String, page: String): String =
        "https://run.mocky.io/v3/06344bb7-b66e-45b2-bae3-e8fad9eb7c91/" +
                "category/$category/page/$page"


}