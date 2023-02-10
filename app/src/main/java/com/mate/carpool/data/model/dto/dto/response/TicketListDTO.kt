package com.mate.carpool.data.model.dto.dto.response

import com.google.gson.annotations.SerializedName
import com.mate.carpool.data.model.domain.domain.DriverModel
import com.mate.carpool.data.model.domain.domain.TicketModel
import com.mate.carpool.util.formatStartTime

data class TicketListDTO(
    @SerializedName("carpoolId") val id: String,
    @SerializedName("departureArea") val startArea: String,
    @SerializedName("departureTime") val startTime: String,
    @SerializedName("driverImageUrl") val driverImage: String,
    @SerializedName("currentPerson") val currentPerson: Int,
    @SerializedName("recruitPerson") val recruitPerson: Int,
    @SerializedName("boardingPrice") val ticketPrice: Int,
    @SerializedName("available") val available: Boolean
) {
    fun asTicketListDomainModel() = TicketModel(
        id = id,
        profileImage = driverImage,
        startArea = startArea,
        endArea = "",
        boardingPlace = "",
        startTime = startTime.formatStartTime(),
        openChatUrl = "",
        recruitPerson = recruitPerson,
        currentPerson = currentPerson,
        ticketPrice = ticketPrice,
        available = available,
        driver = DriverModel.getInitValue(),
        passenger = emptyList()
    )

}
