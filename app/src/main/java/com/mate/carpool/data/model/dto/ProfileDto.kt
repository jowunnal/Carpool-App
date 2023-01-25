package com.mate.carpool.data.model.dto

import com.mate.carpool.data.model.domain.TicketCostType
import com.mate.carpool.data.model.domain.DayStatus
import com.mate.carpool.data.model.domain.Profile
import com.mate.carpool.data.model.domain.StartArea
import com.mate.carpool.data.model.domain.Ticket
import com.mate.carpool.data.model.domain.TicketStatus
import com.mate.carpool.data.model.domain.UserRole
import com.mate.carpool.data.model.dto.common.TimeOfUseDto
import com.mate.carpool.ui.util.date
import com.mate.carpool.ui.util.hour
import com.mate.carpool.ui.util.minute
import com.mate.carpool.ui.util.month
import com.mate.carpool.ui.util.year
import java.util.Calendar

data class ProfileDto(
    val profileImage: String,
    val memberName: String,
    val studentNumber: String,
    val department: String,
    val phoneNumber: String,
    val memberTimeTable: List<TimeOfUseDto> = emptyList(),
    val memberRole: String,
    val tickets: List<TicketDto> = emptyList()
) {

    fun toDomain() = Profile(
        profileImage = profileImage,
        name = memberName,
        studentId = studentNumber,
        department = department,
        phone = phoneNumber,
        daysOfUse = memberTimeTable.map { it.toDomain() },
        userRole = when (memberRole) {
            "DRIVER" -> UserRole.DRIVER
            "PASSENGER" -> UserRole.PASSENGER
            "ADMIN" -> UserRole.ADMIN
            else -> throw IllegalStateException("[ProfileDto.toDomain] memberRole = $memberRole")
        },
        recentTickets = tickets.map { it.toDomain() }
    )
}

data class TicketDto(
    val id: Long,
    val profileImage: String,
    val startArea: String,
    val dayStatus: String,
    val startTime: String,
    val recruitPerson: Int,
    val currentPersonCount: Int,
    val ticketStatus: String,
    val ticketType: String,
) {

    fun toDomain() = Ticket(
        id = id,
        thumbnail = profileImage,
        startArea = StartArea.findByDisplayName(displayName = startArea),
        dayStatus = when (dayStatus) {
            "MORNING" -> DayStatus.AM
            "AFTERNOON" -> DayStatus.PM
            else -> throw IllegalStateException("[TicketDto.toDomain] dayStatus = $dayStatus")
        },
        startTime = startTime.run {
            val cal = Calendar.getInstance()
            cal.year = substring(0..3).toInt()
            cal.month = substring(4..5).toInt()
            cal.date = substring(6..7).toInt()
            cal.hour = substring(8..9).toInt()
            cal.minute = substring(10..11).toInt()
            cal.timeInMillis
        },
        maximumNumber = recruitPerson,
        currentNumber = currentPersonCount,
        status = when (ticketStatus) {
            "BEFORE" -> TicketStatus.BEFORE
            "ING" -> TicketStatus.ING
            "CANCEL" -> TicketStatus.CANCEL
            "AFTER" -> TicketStatus.AFTER
            else -> throw IllegalStateException("[TicketDto.toDomain] ticketStatus = $ticketStatus")
        },
        costType = when (ticketType) {
            "FREE" -> TicketCostType.FREE
            "COST" -> TicketCostType.COST
            else -> throw IllegalStateException("[TicketDto.toDomain] ticketType = $ticketType")
        }
    )
}
