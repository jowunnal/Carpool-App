package com.mate.carpool.data.model.DTO

data class MemberResponseDTO(
    val passengerId:Long,
    val email:String,
    val studentNumber:String,
    val memberName:String,
    val department:String,
    val phoneNumber:String,
    val auth:String,
    val profileImage:String,
    val area:String,
    val member:String
)