package com.kharazmic.app

class Address {


    val LoginAPI = "https://run.mocky.io/v3/05bb668b-4b7f-4a2a-8cce-4ef289072c52"

    val ValidatePhoneAPI = "https://run.mocky.io/v3/05bb668b-4b7f-4a2a-8cce-4ef289072c52"

    fun NewsAPI(category: String): String =
        "https://run.mocky.io/v3/54765ba3-f3c6-40ec-8618-f8dd5e1ee4bf?category=$category"

    fun TutorialAPI(category: String): String =
        "https://run.mocky.io/v3/54765ba3-f3c6-40ec-8618-f8dd5e1ee4bf?category=$category"

}