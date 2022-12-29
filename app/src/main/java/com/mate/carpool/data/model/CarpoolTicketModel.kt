package com.mate.carpool.data.model

/*
    ticket Model
*/

data class CarpoolTicketModel(
    var startArea:String = "",
    val endArea:String="경운대학교",
    var boardingPlace:String="",
    var startDayMonth:String="",
    var dayStatus:String="",
    var startTime:String="",
    var openChatUrl: String="",
    var recruitPerson: Int=0,
    var ticketType: String="",
    val ticketPrice: Int=0)