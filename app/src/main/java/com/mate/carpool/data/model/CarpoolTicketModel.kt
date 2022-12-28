package com.mate.carpool.data.model

/*
    ticket Model
*/

data class CarpoolTicketModel(
    var startArea:String,
    val endArea:String,
    var boardingPlace:String,
    var startDayMonth:String,
    var dayStatus:String,
    var startTime:String,
    var openChatUrl: String,
    var recruitPerson: Int,
    var ticketType: String,
    val ticketPrice: Int){
    constructor():this("","경운대학교","","","","","",0,"",0)
}