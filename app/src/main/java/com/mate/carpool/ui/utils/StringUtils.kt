package com.mate.carpool.ui.utils

object StringUtils {

    @JvmStatic
    fun String.asTicketTypeToDomain() =
        when(this){
            "FREE" -> "무료"
            "COST" -> "유료"
            else -> ""
        }

    @JvmStatic
    fun String.asDayStatusToDomain() =
        when(this){
            "MORNING" -> "오전"
            "EVENING" -> "오후"
            else -> ""
        }

    @JvmStatic
    fun String.asStartTimeToDomain() = StringBuffer(this).insert(2,':').toString()

    @JvmStatic
    fun String.asStartDayMonthToDomain() = StringBuffer(this).insert(2,'/').toString()
}