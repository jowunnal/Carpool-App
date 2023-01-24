package com.mate.carpool.ui.screen.home.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.domain.item.DayStatus
import com.mate.carpool.data.model.domain.item.MemberRole
import com.mate.carpool.data.model.domain.item.TicketStatus
import com.mate.carpool.data.model.domain.item.TicketType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object PreviewHomeBottomSheetViewModel: ViewModel(),HomeBottomSheetViewModelInterface {
    override val mutableTicketId: MutableStateFlow<Long> =MutableStateFlow(0L)
    override val ticketId: StateFlow<Long> get() = mutableTicketId.asStateFlow()
    override val newPassengerState: StateFlow<Boolean> get() = MutableStateFlow(false).asStateFlow()
    override val memberModel: MutableStateFlow<MemberModel> = MutableStateFlow(MemberModel(
        UserModel(
            "진호",
            "20173000",
            "동의대학교",
            "010-1234-5678",
            MemberRole.Driver,
            "",
            emptyList()
        ),
        listOf(TicketListModel(
            1,
            "",
            "인동",
            "08:00",
            3,
            1,
            TicketType.Cost,
            TicketStatus.Before,
            DayStatus.Morning
        ))
    ))
    override val toastMessage: StateFlow<String>
        get() = MutableStateFlow("").asStateFlow()
    override val initViewState: MutableState<Boolean> = mutableStateOf(false)
    override val carpoolTicketState: StateFlow<TicketModel> get() = MutableStateFlow(TicketModel(
        1,
        "황진호",
        "인동",
        "경운대학교",
        "경운대학교앞",
        "01/21",
        DayStatus.Morning,
        "08:00",
        "link",
    3,
        TicketType.Cost,
        20000,
        listOf(
            UserModel(
                "진호",
                "20173000",
                "동의대학교",
                "010-1234-5678",
                MemberRole.Driver,
                "",
                emptyList()
            )
        )
    )).asStateFlow()

    override fun addNewPassengerToTicket(id: Long) {
    }

    override suspend fun newPassengerResponse(message: String, statue: Boolean) {
    }

    override suspend fun initToastMessage() {
    }

    override suspend fun initNewPassengerState() {
    }
}