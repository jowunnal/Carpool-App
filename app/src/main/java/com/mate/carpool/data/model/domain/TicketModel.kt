package com.mate.carpool.data.model.domain

/*
    ticket Model
*/

data class TicketModel(
    var memberName:String = "",
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