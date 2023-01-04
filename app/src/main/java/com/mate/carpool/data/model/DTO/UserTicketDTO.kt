package com.mate.carpool.data.model.DTO

data class UserTicketDTO(val id:Int = 0,
                         val profileImage:String = "",
                         val startArea:String = "",
                         val dayStatus:String = "",
                         val startTime:String = "",
                         val recruitPerson:Int = 0,
                         val currentPersonCount:Int = 0,
                         val ticketStatus:String = "",
                         val ticketType: String="")

