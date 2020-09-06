package com.kharazmic.app

class Address {

    private val base = "http://136.243.140.228:1337"


    fun loginAPI(phone: String) = "$base/people/genpassword?mobile=$phone"


    val validatePhoneAPI = "$base/auth/local"

    fun stockSearch(keyword: String, page: String): String =
        "https://run.mocky.io/v3/a16de51a-5b19-4110-afa9-487e1606c22d?keyword=$keyword&page=$page"


    val updateInfoAPI = "https://run.mocky.io/v3/8763b1c1-2bec-4b35-badc-7d9adc753df7"

    val userInfoAPI = "$base/people/profile"

    val ticketAPI = "https://run.mocky.io/v3/082ecf40-a6f3-44ef-a9f3-1568d2f299a8"

    val signalDetailAPI = "https://run.mocky.io/v3/fb70bab7-217b-4e3f-a7b8-8d2955834678"

    val bestStockAPI = "https://run.mocky.io/v3/545a2971-38a5-4ea1-bc81-038614a9b096"

    val gold = "https://run.mocky.io/v3/b273e6f1-f6e3-4597-ab2e-ce2a83fdde23"

    val currency = "https://run.mocky.io/v3/814683d7-b505-424d-8fb0-682bcca8f7bc"


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