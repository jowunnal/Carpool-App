package com.mate.carpool.util

import android.telephony.PhoneNumberUtils
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.item.TicketType
import com.mate.carpool.ui.util.*
import java.util.*

fun String.formatPhoneNumber(): String = try {
    PhoneNumberUtils.formatNumber(this, Locale.KOREA.country)
} catch (e: NullPointerException) {
    this
}

fun String.substring(maxLength: Int) =
    if (length <= maxLength) this else substring(0 until maxLength)

fun String.formatStartTime(): Long =
    this.run {
        val cal = Calendar.getInstance()
        cal.year = substring(0..3).toInt()
        cal.month = substring(4..5).toInt()
        cal.date = substring(6..7).toInt()
        cal.hour = substring(8..9).toInt()
        cal.minute = substring(10..11).toInt()
        cal.timeInMillis
    }

fun String.asTicketTypeToDomain() =
    when(this){
        "FREE" -> TicketType.Free
        "COST" -> TicketType.Cost
        else -> TicketType.Cost
    }

fun String.asDayStatusToDomain() =
    when(this){
        "MORNING" -> DayStatus.Morning
        "Afternoon" -> DayStatus.Afternoon
        else -> DayStatus.Afternoon
    }

fun String.asTicketStatusToDomain() =
    when(this){
        "CANCEL" -> TicketStatus.Cancel
        "BEFORE" -> TicketStatus.Before
        "ING" -> TicketStatus.Ing
        "AFTER" -> TicketStatus.After
        else -> TicketStatus.After
    }

fun String.asStartTimeToDomain() = StringBuffer(this).insert(2,':').toString()

fun String.asStartDayMonthToDomain() = StringBuffer(this).insert(2,'/').toString()

fun String.asMemberRoleToDomain() =
    when(this){
        "PASSENGER" -> MemberRole.Passenger
        "DRIVER" -> MemberRole.Driver
        else -> MemberRole.Driver
    }