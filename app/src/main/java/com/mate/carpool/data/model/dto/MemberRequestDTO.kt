package com.mate.carpool.data.model.dto

data class MemberRequestDTO(
    val studentNumber:String,
    val memberName: String,
    var department:String,
    val phoneNumber: String,
    var auth:String,
    var area:String,
    var memberTimeTable: List<MemberTimeTableRequestDTO>?
)
