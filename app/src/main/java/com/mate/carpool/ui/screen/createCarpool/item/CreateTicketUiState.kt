package com.mate.carpool.ui.screen.createCarpool.item

import com.mate.carpool.data.model.domain.StartArea
import com.mate.carpool.data.model.domain.domain.TicketModel
import com.mate.carpool.ui.util.hour
import com.mate.carpool.ui.util.minute
import java.util.*

data class CreateTicketUiState(
    val driverName: String,
    val driverProfile: String,
    val startArea: StartArea,
    val boardingPlace: String,
    val startTime: String,
    val openChatLink: String,
    val recruitNumber: String,
    val fee: String,
    val invalidArea: Boolean,
    val invalidStartTime: Boolean,
    val invalidOpenChatLink: Boolean,
    val invalidRecruitNumber: Boolean,
    val invalidFee: Boolean
) {
    fun asTicketDomainModel() = TicketModel(
        id = -1L,
        profileImage = driverProfile,
        memberName = driverName,
        startArea = startArea.displayName,
        endArea = "",
        boardingPlace = boardingPlace,
        startTime = startTime.run {
            val cal = Calendar.getInstance().apply {
                add(Calendar.DATE,1)
                hour = this@run.split(":")[0].toInt()
                minute = this@run.split(":")[1].toInt()
            }
            cal.timeInMillis
        },
        openChatUrl = openChatLink,
        recruitPerson = recruitNumber.replace(",","").toInt(),
        ticketPrice = fee.toInt(),
        passenger = emptyList()
    )

    companion object {
        fun getInitValue() = CreateTicketUiState(
            driverName = "홍길동",
            driverProfile = "",
            startArea = StartArea.ETC,
            boardingPlace = "",
            startTime = "",
            openChatLink = "",
            recruitNumber = "",
            fee = "",
            invalidArea = false,
            invalidStartTime = false,
            invalidOpenChatLink = false,
            invalidRecruitNumber = false,
            invalidFee = false
        )
    }
}