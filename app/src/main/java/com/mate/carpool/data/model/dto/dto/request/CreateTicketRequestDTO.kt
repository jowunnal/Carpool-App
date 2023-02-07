package com.mate.carpool.data.model.dto.dto.request

import com.google.gson.annotations.SerializedName

data class CreateTicketRequestDTO(
    @SerializedName("departureArea") val startArea: String,
    @SerializedName("departureTime") val startTime: String,
    @SerializedName("arrivalArea") val endArea: String,
    @SerializedName("boardingPlace") val boardingPlace: String,
    @SerializedName("openChatUrl") val openChatUrl: String,
    @SerializedName("recruitPerson") val recruitPerson: Int,
    @SerializedName("boardingPrice") val ticketPrice: Int
)
