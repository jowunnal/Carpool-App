package com.mate.carpool.data.model.dto.request

import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.data.model.dto.common.TimeOfUseDto
import java.time.DayOfWeek

data class UpdateMyProfileRequest(
    val phoneNumber: String,
    val auth: String,
    val memberTimeTable: List<TimeOfUseDto>
) {

    companion object {

        fun fromDomain(
            phone: String,
            userRole: MemberRole,
            daysOfUse: List<DayOfWeek>
        ) = UpdateMyProfileRequest(
            phoneNumber = phone,
            auth = when (userRole) {
                MemberRole.PASSENGER -> "PASSENGER"
                MemberRole.DRIVER -> "DRIVER"
                MemberRole.ADMIN -> "ADMIN"
            },
            memberTimeTable = daysOfUse.map { TimeOfUseDto.fromDomain(it) }
        )
    }
}
