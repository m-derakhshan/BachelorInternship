package com.kharazmic.app

class Arrange {



    fun validatePhone(phone: String?): Boolean = !phone.isNullOrEmpty() && phone.length == 11


}