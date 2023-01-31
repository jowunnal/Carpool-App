package com.mate.carpool.data.model.item

import androidx.compose.runtime.Stable

/**
 * 월~금 enum class
 * @property getWeekCode DTO 타입의 숫자코드 1~5 return
 */

@Stable
enum class WeekItem{
    Mon,
    Tues,
    Wed,
    Thurs,
    Fri
}

fun List<WeekItem>.getWeekCode() = map { it.getWeekCode() }

fun WeekItem.getWeekCode() : String =
    when(this){
        WeekItem.Mon -> "1"
        WeekItem.Tues -> "2"
        WeekItem.Wed -> "3"
        WeekItem.Thurs -> "4"
        WeekItem.Fri -> "5"
    }

fun getWeekItem(str:String) : WeekItem? =
    when(str){
        "월" -> WeekItem.Mon
        "화" -> WeekItem.Tues
        "수" -> WeekItem.Wed
        "목" -> WeekItem.Thurs
        "금" -> WeekItem.Fri
        else -> null
    }

