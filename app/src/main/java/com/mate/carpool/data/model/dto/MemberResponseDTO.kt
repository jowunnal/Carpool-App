package com.mate.carpool.data.model.dto

import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.ui.util.StringUtils.asMemberRoleToDomain

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
) {
    fun asUserDomain() = UserModel(
        this.memberName,
        this.studentNumber,
        this.department,
        this.phoneNumber,
        this.auth.asMemberRoleToDomain(),
        this.profileImage,
        emptyList(),
        this.passengerId
    )
}