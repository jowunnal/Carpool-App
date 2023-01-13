package com.mate.carpool.data.model.DTO

import com.mate.carpool.data.model.domain.UserModel

data class MemberRequestDTO(val studentNumber:String,
                            val memberName: String,
                            var department:String,
                            val phoneNumber: String,
                            var auth:String,
                            var area:String,
                            var memberTimeTable: List<MemberTimeTableResponseDTO>?)
{
    constructor(userModel: UserModel):this(userModel.studentID,userModel.name,userModel.studentDepartment,userModel.studentPhone.get()!!,userModel.studentType,"dd",
    listOf())
}
