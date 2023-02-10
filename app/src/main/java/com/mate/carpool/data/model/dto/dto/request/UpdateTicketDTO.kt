package com.mate.carpool.data.model.dto.dto.request

import com.google.gson.annotations.SerializedName

data class UpdateTicketDTO(
    @SerializedName("carpoolId") val id: String,
    @SerializedName("departureArea") val startArea: String,
    @SerializedName("departureTime") val startTime: String,
    @SerializedName("arrivalArea") val endArea: String,
    @SerializedName("boardingPlace") val boardingPlace: String,
    @SerializedName("openChatUrl") val openChatUrl: String,
    @SerializedName("recruitPerson") val recruitPerson: Int,
    @SerializedName("boardingPrice") val ticketPrice: Int
) {
    companion object {
        fun fromDomain(
            id: String,
            startArea: String,
            startTime: String,
            endArea: String,
            boardingPlace: String,
            openChatUrl: String,
            recruitPerson: Int,
            ticketPrice: Int
        ) = UpdateTicketDTO(
            id = id,
            startArea = startArea,
            startTime = startTime,
            endArea = endArea,
            boardingPlace = boardingPlace,
            openChatUrl = openChatUrl,
            recruitPerson = recruitPerson,
            ticketPrice = ticketPrice
        )
    }
}
