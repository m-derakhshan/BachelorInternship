package com.kharazmic.app

class Address {

    val base = "http://95.216.62.137/api/v1"


    val LoginAPI = "$base/register/register"
    val ValidatePhoneAPI = "$base/register/registerConfirm"
//
//    val LoginAPI = "https://run.mocky.io/v3/05bb668b-4b7f-4a2a-8cce-4ef289072c52"
//    val ValidatePhoneAPI = "https://run.mocky.io/v3/7c2cd717-96b8-439c-b6e3-8315980d1e9d"
//


    val UpdateInfoAPI = "https://run.mocky.io/v3/8763b1c1-2bec-4b35-badc-7d9adc753df7"
    val UserInfoAPI = "https://run.mocky.io/v3/8763b1c1-2bec-4b35-badc-7d9adc753df7"



    val TicketAPI = "https://run.mocky.io/v3/082ecf40-a6f3-44ef-a9f3-1568d2f299a8"




    fun NewsAPI(keyword: String, category: String, page: Int): String =
        "https://run.mocky.io/v3/ac5af763-26b2-4ae7-9b01-ab4635abce96/" +
                "category/$category/" + "keyword/$keyword/" + "page/$page"

    fun TutorialAPI(keyword: String, category: String, page: Int): String =
        "https://run.mocky.io/v3/ac5af763-26b2-4ae7-9b01-ab4635abce96/" +
                "category/$category/" + "keyword/$keyword/" + "page/$page"

    fun SignalsAPI(category: String): String =
        "https://run.mocky.io/v3/06344bb7-b66e-45b2-bae3-e8fad9eb7c91/" +
                "category/$category"


}