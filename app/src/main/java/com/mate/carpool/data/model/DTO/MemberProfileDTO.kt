package com.mate.carpool.data.model.DTO
/*

 */
data class MemberProfileDTO(
    val profileImage:String = "",
    val memberName:String = "",
    val studentNumber:String = "",
    val department:String = "",
    val phoneNumber:String = "",
    val memberTimeTable:List<MemberTimeTableResponseDTO> ?= null,
    val memberRole:String = "",
    val tickets:List<UserTicketDTO> ?= null
)
