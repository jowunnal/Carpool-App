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
    Calendar.getInstance().apply {
        year = substring(0..3).toInt()
        month = substring(5..6).toInt()
        date = substring(8..9).toInt()
        hour = substring(11..12).toInt()
        minute = substring(14..15).toInt()
    }.timeInMillis


fun String.asStartTimeDomain(): String =
    this.run {
        val cal = Calendar.getInstance().apply {
            add(Calendar.DATE,1)
            hour = this@run.split(":")[0].toInt()
            minute = this@run.split(":")[1].toInt()
        }
        String.format(Locale.KOREA,"%tF %tH-%tM-%tS",cal.timeInMillis,cal.hour,cal.minute,cal.second)
    }

fun String.asTicketTypeToDomain() =
    when(this){
        "FREE" -> TicketType.Free
        "COST" -> TicketType.Cost
        else -> TicketType.Cost
    }

fun String.asDayStatusToDomain() =
    when(this){
        "MORNING" -> DayStatus.AM
        "Afternoon" -> DayStatus.PM
        else -> throw IllegalStateException("[String.asDayStatusToDomain] Status = $this")
    }

fun String.asTicketStatusToDomain() =
    when(this){
        "CANCEL" -> TicketStatus.Cancel
        "BEFORE" -> TicketStatus.Before
        "ING" -> TicketStatus.Ing
        "AFTER" -> TicketStatus.After
        else -> TicketStatus.After
    }

fun String.asMemberRoleToDomain() =
    when(this){
        "PASSENGER" -> MemberRole.PASSENGER
        "DRIVER" -> MemberRole.DRIVER
        "ADMIN" -> MemberRole.ADMIN
        else -> throw IllegalStateException("[String.asMemberRoleToDomain] string = $this")
    }