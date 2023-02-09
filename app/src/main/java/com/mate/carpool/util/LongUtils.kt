package com.mate.carpool.util

import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.ui.util.*
import java.util.*
import kotlin.time.Duration.Companion.minutes

fun Long.formatStartTimeToDTO() = this.run {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    String.format(Locale.KOREA,"%02d:%02d",cal.hour,cal.minute)
}

fun Long.formatStartDayMonthToDTO() = this.run {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    String.format(Locale.KOREA,"%02d/%02d",cal.month,cal.date)
}

fun Long.formatStartTimeAsDisplay() = this.run {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    if(cal.minute != 0)
        String.format(Locale.KOREA,"%d시 %b분",cal.hour, cal.minute )
    else
        String.format(Locale.KOREA,"%d시",cal.hour )
}

fun Long.startTimeAsRequestDTO() = this.run {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    String.format(Locale.KOREA,"%tF %tH-%tM-%tS",cal.timeInMillis,cal.hour,cal.minute,cal.second)
}

fun Long.startTimeToDayStatus() = this.run {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    when(cal.hour < 12) {
        true -> DayStatus.AM
        false -> DayStatus.PM
    }
}