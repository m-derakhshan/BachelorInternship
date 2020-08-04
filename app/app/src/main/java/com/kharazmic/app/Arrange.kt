package com.kharazmic.app

class Arrange {


    fun validatePhone(phone: String?): Boolean = !phone.isNullOrEmpty() && phone.length == 11

    fun persianConverter(number: String?): String {
        var result: String = ""
        if (number.isNullOrEmpty())
            return ""
        for (i in number)
            result += when (i) {
                '0' -> '۰'
                '1' -> '۱'
                '2' -> '۲'
                '3' -> '۳'
                '4' -> '۴'
                '5' -> '۵'
                '6' -> '۶'
                '7' -> '۷'
                '8' -> '۸'
                '9' -> '۹'
                else -> i
            }
        return result
    }

}