package com.mate.carpool.data.model.DTO

import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.util.EncapsulationUtil.asTicketListDomain
import com.mate.carpool.ui.util.StringUtils.asMemberRoleToDomain

data class MemberProfileDTO(
    val profileImage:String = "",
    val memberName:String = "",
    val studentNumber:String = "",
    val department:String = "",
    val phoneNumber:String = "",
    val memberTimeTable:List<MemberTimeTableResponseDTO> ?= null,
    val memberRole:String = "",
    val tickets:List<UserTicketDTO> ?= null
) {

    fun asStatusDomain() = MemberModel(
        UserModel(
            this.memberName,
            this.studentNumber,
            this.department,
            this.phoneNumber,
            this.memberRole.asMemberRoleToDomain(),
            this.profileImage,
            emptyList(),
            -1
        ),
        this.tickets?.asTicketListDomain()
    )

}
