package com.mate.carpool.data.model

/*
회원이름,학번,학과,전화번호,유형(드라이버,패신저),프로필사진,이동할요일
 */
data class UserModel(
    val name: String,
    val studentID:String,
    val studentDepartment:String,
    val studentPhone:String,
    val studentType:String,
    val studentProfile:String,
    val studentDayCodes:ArrayList<Int>)