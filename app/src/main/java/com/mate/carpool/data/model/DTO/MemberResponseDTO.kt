package com.mate.carpool.data.model.DTO

data class MemberResponseDTO(
    val token:String,
    val email:String,
    val studentNumber:String,
    val memberName:String,
    val department:String,
    val phoneNumber:String,
    val auth:String,
    val profileImage:String,
    val area:String,
    val memberTimeTable:ArrayList<MemberTimeTableResponseDTO>,
    val member:String
)