package com.mate.carpool.util

import com.mate.carpool.data.model.dto.MemberProfileDTO
import com.mate.carpool.data.model.dto.MemberResponseDTO
import com.mate.carpool.data.model.dto.TicketDetailResponseDTO
import com.mate.carpool.data.model.dto.UserTicketDTO
import com.mate.carpool.data.model.response.ApiResponse

object EncapsulationUtil {
    @JvmStatic
    fun List<UserTicketDTO>.asTicketListDomain() = map { it.asTicketListDomain() }

    @JvmStatic
    fun ApiResponse.SuccessResponse<MemberProfileDTO>.asStatusDomain() =
        ApiResponse.SuccessResponse(this.responseMessage.asStatusDomain())

    @JvmStatic
    fun List<MemberResponseDTO>.asUserDomain() = map { it.asUserDomain() }

    @JvmStatic
    fun ApiResponse.SuccessResponse<List<UserTicketDTO>>.asTicketListDomain() = ApiResponse.SuccessResponse(this.responseMessage.asTicketListDomain())

    @JvmStatic
    fun ApiResponse.SuccessResponse<TicketDetailResponseDTO>.asTicketDomain() = ApiResponse.SuccessResponse(this.responseMessage.asTicketDomain())
}