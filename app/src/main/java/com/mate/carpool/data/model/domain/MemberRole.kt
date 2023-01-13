package com.mate.carpool.data.model.domain

data class MemberRole(
    var studentNumber:String="",
    var memberRole:String="",
    val ticketList:List<TicketListModel> ?= null
)
