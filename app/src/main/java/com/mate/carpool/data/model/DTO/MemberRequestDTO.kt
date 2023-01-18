package com.mate.carpool.data.model.DTO

data class MemberRequestDTO(
    val studentNumber:String,
    val memberName: String,
    var department:String,
    val phoneNumber: String,
    var auth:String,
    var area:String,
    var memberTimeTable: List<MemberTimeTableRequestDTO>?
)
