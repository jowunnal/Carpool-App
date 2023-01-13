package com.mate.carpool.ui.utils

object ButtonCheckUtils {
    /*
    입력된 문자열(inputString)이 패턴(pattern)과 일치하고, maxLength 보다작고, minLength 보다 큰 기준에 부합하면 false 를 반환
    그렇지않으면 true 반환
     */
    @JvmStatic
    fun checkRegisterInfoIsCorrect(inputString: String, pattern:String, textMaxLength:Int, textMinLength:Int):Boolean{
        var isTrue = true
        if(inputString.contains(pattern.toRegex()) || (inputString.length>textMaxLength) || (inputString.isEmpty()) || (inputString.length<textMinLength)){
            isTrue = false
        }
        return isTrue
    }
}