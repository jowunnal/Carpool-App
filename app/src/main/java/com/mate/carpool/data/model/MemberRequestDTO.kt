package com.mate.carpool.data.model

data class MemberRequestDTO(val studentNumber:String,
                            val memberName: String,
                            var department:String,
                            val phoneNumber: String,
                            var auth:String,
                            var area:String,
                            var memberTimeTable: List<MemberTimetableRequestDTO>?){
    constructor(userModel: UserModel):this(userModel.studentID,userModel.name,userModel.studentDepartment,userModel.studentPhone.get()!!,userModel.studentType,"dd",
    userModel.studentDayCodes)
}

data class MemberTimetableRequestDTO(var dayCode:String)