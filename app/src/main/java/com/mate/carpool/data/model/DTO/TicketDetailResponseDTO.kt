package com.mate.carpool.data.model.DTO

/*

 */
data class TicketDetailResponseDTO(val id:Int=0,
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
                                   val passengers:List<MemberResponseDTO>?=null)