package com.mate.carpool.data.model.domain
/*
프로필이미지
출발지
출발시간
정원
현재인원
유 or 무료
오전인지 오후인지
 */
data class TicketListModel(
    val id:Int = 0,
    val profileImage:String ?= "",
    val startArea:String ?= "",
    val startTime:String ?= "",
    val recruitPerson:Int ?= 0,
    val currentPersonCount:Int ?= 0,
    val ticketType:String ?= "",
    val ticketStatus:String ?= "",
    val dayStatus:String ?= ""
)