package com.kharazmic.app

class Address {


    val LoginAPI = "https://run.mocky.io/v3/05bb668b-4b7f-4a2a-8cce-4ef289072c52"

    val ValidatePhoneAPI = "https://run.mocky.io/v3/05bb668b-4b7f-4a2a-8cce-4ef289072c52"

    fun NewsAPI(category: String): String =
        "https://run.mocky.io/v3/ac5af763-26b2-4ae7-9b01-ab4635abce96?category=$category"

    fun TutorialAPI(category: String): String =
        "https://run.mocky.io/v3/ac5af763-26b2-4ae7-9b01-ab4635abce96?category=$category"

}