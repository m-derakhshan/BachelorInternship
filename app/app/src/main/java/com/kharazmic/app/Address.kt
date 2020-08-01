package com.kharazmic.app

class Address {


    val LoginAPI = "https://run.mocky.io/v3/05bb668b-4b7f-4a2a-8cce-4ef289072c52"

    val ValidatePhoneAPI = "https://run.mocky.io/v3/05bb668b-4b7f-4a2a-8cce-4ef289072c52"


    val UserInfoAPI = "https://run.mocky.io/v3/4a0d2b23-1030-41b5-a002-2b0181bbd90d"


    fun NewsAPI(keyword: String, category: String, page: Int): String =
        "https://run.mocky.io/v3/ac5af763-26b2-4ae7-9b01-ab4635abce96?" +
                "category=$category &" + "keyword=$keyword &" + "page=$page"

    fun TutorialAPI(keyword: String, category: String, page: Int): String =
        "https://run.mocky.io/v3/ac5af763-26b2-4ae7-9b01-ab4635abce96?" +
                "category=$category &" + "keyword=$keyword &" + "page=$page"

}