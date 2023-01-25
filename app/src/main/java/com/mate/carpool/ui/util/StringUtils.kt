package com.mate.carpool.ui.util

import com.mate.carpool.data.model.domain.item.DayStatus
import com.mate.carpool.data.model.domain.item.MemberRole
import com.mate.carpool.data.model.domain.item.TicketStatus
import com.mate.carpool.data.model.domain.item.TicketType

object StringUtils {

    @JvmStatic
    fun String.asTicketTypeToDomain() =
        when(this){
            "FREE" -> TicketType.Free
            "COST" -> TicketType.Cost
            else -> TicketType.Cost
        }

    @JvmStatic
    fun String.asDayStatusToDomain() =
        when(this){
            "MORNING" -> DayStatus.Morning
            "Afternoon" -> DayStatus.Afternoon
            else -> DayStatus.Afternoon
        }

    @JvmStatic
    fun String.asTicketStatusToDomain() =
        when(this){
            "CANCEL" -> TicketStatus.Cancel
            "BEFORE" -> TicketStatus.Before
            "ING" -> TicketStatus.Ing
            "AFTER" -> TicketStatus.After
            else -> TicketStatus.After
        }

    @JvmStatic
    fun String.asStartTimeToDomain() = StringBuffer(this).insert(2,':').toString()

    @JvmStatic
    fun String.asStartDayMonthToDomain() = StringBuffer(this).insert(2,'/').toString()

    @JvmStatic
    fun String.asMemberRoleToDomain() =
        when(this){
            "PASSENGER" -> MemberRole.Passenger
            "DRIVER" -> MemberRole.Driver
            else -> MemberRole.Driver
        }
}