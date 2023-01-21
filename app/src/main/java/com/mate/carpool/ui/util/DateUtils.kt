package com.mate.carpool.ui.util

import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

var Calendar.year
    get() = get(Calendar.YEAR)
    set(value) {
        set(Calendar.YEAR, value)
    }

var Calendar.month
    get() = get(Calendar.MONTH) + 1
    set(value) {
        set(Calendar.MONTH, value - 1)
    }

var Calendar.date
    get() = get(Calendar.DATE)
    set(value) {
        set(Calendar.DATE, value)
    }

var Calendar.hour
    get() = get(Calendar.HOUR)
    set(value) {
        set(Calendar.HOUR, value)
    }

var Calendar.minute
    get() = get(Calendar.MINUTE)
    set(value) {
        set(Calendar.MINUTE, value)
    }

var Calendar.second
    get() = get(Calendar.SECOND)
    set(value) {
        set(Calendar.SECOND, value)
    }


val DayOfWeek.displayName: String
    get() = getDisplayName(TextStyle.SHORT, Locale.KOREA)


object DateUtil {

    /**
     * 카풀 서비스를 이용할 수 있는 요일.
     *
     * 현재는 평일에만 서비스를 이용할 수 있다.
     */
    val availableDay = listOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
    )
}