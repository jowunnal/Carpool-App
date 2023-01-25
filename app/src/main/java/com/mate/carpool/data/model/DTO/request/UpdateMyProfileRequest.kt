package com.mate.carpool.data.model.dto.request

import com.mate.carpool.data.model.domain.UserRole
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
            userRole: UserRole,
            daysOfUse: List<DayOfWeek>
        ) = UpdateMyProfileRequest(
            phoneNumber = phone,
            auth = when (userRole) {
                UserRole.PASSENGER -> "PASSENGER"
                UserRole.DRIVER -> "DRIVER"
                UserRole.ADMIN -> "ADMIN"
            },
            memberTimeTable = daysOfUse.map { TimeOfUseDto.fromDomain(it) }
        )
    }
}
