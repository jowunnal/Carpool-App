package com.mate.carpool.data.model.dto
/*
 */
data class UserCarpoolDetailDTO (
    val id:Long=0,
    val memberName:String="",
    val profileImage:String="",
    var startArea:String = "",
    val endArea:String="경운대학교",
    var boardingPlace:String="",
    var startDayMonth:String="",
    var dayStatus:String="",
    var startTime:String="",
    var openChatUrl: String="",
    var recruitPerson: Int=0,
    var ticketType: String="",
    val ticketPrice: Int=0,
    val ticketStatus: String="CANCEL",
    val passengers:ArrayList<MemberResponseDTO>?=null
)