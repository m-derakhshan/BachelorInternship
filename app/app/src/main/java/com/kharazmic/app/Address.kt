package com.kharazmic.app

class Address {


    val LoginAPI = "https://run.mocky.io/v3/05bb668b-4b7f-4a2a-8cce-4ef289072c52"

    val ValidatePhoneAPI = "https://run.mocky.io/v3/d0b11c14-3479-43ea-9340-4a26c6c1a6f0"


    val UserInfoAPI = "https://run.mocky.io/v3/8763b1c1-2bec-4b35-badc-7d9adc753df7"

    val TicketAPI = "https://run.mocky.io/v3/082ecf40-a6f3-44ef-a9f3-1568d2f299a8"

    fun NewsAPI(keyword: String, category: String, page: Int): String =
        "https://run.mocky.io/v3/ac5af763-26b2-4ae7-9b01-ab4635abce96?" +
                "category=$category&" + "keyword=$keyword&" + "page=$page"

    fun TutorialAPI(keyword: String, category: String, page: Int): String =
        "https://run.mocky.io/v3/ac5af763-26b2-4ae7-9b01-ab4635abce96?" +
                "category=$category&" + "keyword=$keyword&" + "page=$page"

    fun SignalsAPI(category: String, page: Int): String =
        "https://run.mocky.io/v3/2872525b-14d7-4336-b791-176e11caef1b?" +
                "category=$category&" + "page=$page"


}