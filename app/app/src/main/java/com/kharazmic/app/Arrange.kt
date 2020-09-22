package com.kharazmic.app

import java.lang.StringBuilder

class Arrange {


    fun validatePhone(phone: String?): Boolean = !phone.isNullOrEmpty() && phone.length == 11

    fun persianConverter(number: String?): String {
        var result = ""
        if (number.isNullOrEmpty() || number == "null")
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

    fun persianConcatenate(first: String? = "", middle: String? = "", end: String? = ""): String {

        return persianConverter(first) + persianConverter(middle) + persianConverter(end)

    }


    fun concatenate(first: String? = "", middle: String? = "", end: String? = ""): String {
        return (first) + (middle) + (end)
    }


    fun numberEnglishArrangement(number: String): String {
        val tmpNumber = ArrayList<Char>()
        for (i in number)
            tmpNumber.add(i)
        var counter = 0
        for (i in tmpNumber.indices) {
            if (tmpNumber[i] != ',') {
                counter += 1
                if (counter == 3)
                    tmpNumber.add(i + 1, ',').also { counter = 0 }
            }
        }
        val builder = StringBuilder()
        for (i in tmpNumber)
            builder.append(i)
        return builder.toString()
    }


    fun numberPersianArrangement(number: String): String {
        val tmpNumber = ArrayList<Char>()
        for (i in number)
            tmpNumber.add(i)
        var counter = 0
        for (i in tmpNumber.indices) {
            if (tmpNumber[i] != ',') {
                counter += 1
                if (counter == 3 && i != tmpNumber.lastIndex)
                    tmpNumber.add(i + 1, ',').also { counter = 0 }
            }
        }
        val builder = StringBuilder()
        for (i in tmpNumber)
            builder.append(i)
        return this.persianConverter(builder.toString())
    }
}