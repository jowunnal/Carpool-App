package com.mate.carpool.data.model.dto.dto.response

import com.google.gson.annotations.SerializedName
import com.mate.carpool.data.model.domain.domain.DriverModel
import com.mate.carpool.data.model.domain.domain.TicketModel
import com.mate.carpool.data.model.domain.domain.UserModel
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.util.formatStartTime

data class DriverDTO(
    @SerializedName("carImageUrl") val carImage: String,
    @SerializedName("carNumber") val carNumber: String,
    @SerializedName("name") val name: String,
    @SerializedName("profileImageUrl") val profileImage: String
) {
    fun asDriverDomainModel() = DriverModel(
        carImage = carImage,
        carNumber = carNumber,
        phoneNumber = "",
        profileImage = profileImage,
        name = name
    )
}

data class PassengerDTO(
    @SerializedName("passengerId") val passengerId: String,
    @SerializedName("profileImageUrl") val profileImage: String,
    @SerializedName("username") val name: String
) {
    fun asUserDomainModel() = UserModel(
        id = "",
        name = name,
        email = "",
        passWord = "",
        profileImage = profileImage,
        role = MemberRole.PASSENGER,
        passengerId = passengerId
    )
}

data class TicketDetailDTO(
    @SerializedName("carpoolId") val id: String,
    @SerializedName("departureArea") val startArea: String,
    @SerializedName("departureTime") val startTime: String,
    @SerializedName("arrivalArea") val endArea: String,
    @SerializedName("boardingPlace") val boardingPlace: String,
    @SerializedName("openChatUrl") val openChatUrl: String,
    @SerializedName("recruitPerson") val recruitPerson: Int,
    @SerializedName("currentPerson") val currentPerson: Int?,
    @SerializedName("boardingPrice") val ticketPrice: Int,
    @SerializedName("driver") val driver: DriverDTO,
    @SerializedName("passengers") val passenger: List<PassengerDTO>
) {
    fun asTicketDomainModel() = TicketModel(
        id = id,
        profileImage = "",
        startArea = startArea,
        endArea = endArea,
        boardingPlace = boardingPlace,
        startTime = startTime.formatStartTime(),
        openChatUrl = openChatUrl,
        recruitPerson = recruitPerson,
        currentPerson = currentPerson?:0,
        ticketPrice = ticketPrice,
        available = true,
        driver = driver.asDriverDomainModel(),
        passenger = passenger.map { it.asUserDomainModel() }
    )
}