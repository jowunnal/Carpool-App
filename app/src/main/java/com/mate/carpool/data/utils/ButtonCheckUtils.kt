package com.mate.carpool.data.utils

object ButtonCheckUtils {
    @JvmStatic
    fun checkRegisterInfoIsCorrect(inputString: String, pattern:String, textMaxLength:Int, textMinLength:Int):Boolean{
        var isTrue = true
        if(inputString.contains(pattern.toRegex()) || (inputString.length>textMaxLength) || (inputString.isEmpty()) || (inputString.length<textMinLength)){
            isTrue = false
        }
        return isTrue
    }
}