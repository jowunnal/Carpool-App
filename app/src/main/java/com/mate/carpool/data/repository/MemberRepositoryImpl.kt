package com.mate.carpool.data.repository

import com.mate.carpool.data.model.DTO.MemberProfileDTO
import com.mate.carpool.data.model.DTO.UserTicketDTO
import com.mate.carpool.data.model.domain.MemberRole
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.ui.utils.StringUtils.asDayStatusToDomain
import com.mate.carpool.ui.utils.StringUtils.asStartTimeToDomain
import com.mate.carpool.ui.utils.StringUtils.asTicketTypeToDomain
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.utils.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(private val apiService: APIService) : MemberRepository {
    override fun getMemberInfo(): Flow<Any> = handleFlowApi{
        apiService.getMemberMe()
    }.map {
        when(it){
            is ApiResponse.SuccessResponse -> {
                it.responseMessage.asStatusDomain()
            }
            else -> {

            }
        }
    }

    private fun MemberProfileDTO.asStatusDomain() = MemberRole(this.studentNumber,this.memberRole,this.tickets?.asDomain())

    private fun UserTicketDTO.asDomain() = TicketListModel(
        this.id,
        this.profileImage,
        this.startArea,
        this.startTime.asStartTimeToDomain(),
        this.recruitPerson,
        this.currentPersonCount,
        this.ticketType.asTicketTypeToDomain(),
        this.dayStatus.asDayStatusToDomain()
    )
    private fun List<UserTicketDTO>.asDomain() = map { it.asDomain() }
}