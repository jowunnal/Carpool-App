package com.mate.carpool.data.model.dto.common

import com.mate.carpool.ui.util.displayName
import java.time.DayOfWeek

data class TimeOfUseDto(
    val dayCode: String
) {

    fun toDomain() = when (dayCode) {
        "1" -> DayOfWeek.MONDAY
        "2" -> DayOfWeek.TUESDAY
        "3" -> DayOfWeek.WEDNESDAY
        "4" -> DayOfWeek.THURSDAY
        "5" -> DayOfWeek.FRIDAY
        else -> throw IllegalStateException("카풀 서비스는 평일에만 이용할 수 있습니다. daycode = ${dayCode}")
    }
    companion object {

        fun fromDomain(dayOfWeek: DayOfWeek) = TimeOfUseDto(
            dayCode = when (dayOfWeek) {
                DayOfWeek.MONDAY -> "1"
                DayOfWeek.TUESDAY -> "2"
                DayOfWeek.WEDNESDAY -> "3"
                DayOfWeek.THURSDAY -> "4"
                DayOfWeek.FRIDAY -> "5"
                else -> throw IllegalStateException("카풀 서비스는 평일에만 이용할 수 있습니다. daycode = ${dayOfWeek.displayName}")
            }
        )
    }
}