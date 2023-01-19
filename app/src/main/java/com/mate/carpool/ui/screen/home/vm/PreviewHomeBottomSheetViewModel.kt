package com.mate.carpool.ui.screen.home.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object PreviewHomeBottomSheetViewModel: ViewModel(),HomeBottomSheetViewModelInterface {
    override val mutableTicketId: MutableStateFlow<Long> = MutableStateFlow(0L)
    override val ticketId: StateFlow<Long> get() = mutableTicketId.asStateFlow()
    override val mutableNewPassengerState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val newPassengerState: StateFlow<Boolean> get() = mutableNewPassengerState.asStateFlow()
    override val memberModel: MutableStateFlow<MemberModel> = MutableStateFlow(MemberModel())
    override val mutableToastMessage: MutableStateFlow<String> = MutableStateFlow("")
    override val initViewState: MutableState<Boolean> = mutableStateOf(false)
    override val carpoolTicketState: StateFlow<TicketModel>
        get() = MutableStateFlow(TicketModel()).asStateFlow()

    override fun addNewPassengerToTicket(id: Long) {

    }

    override suspend fun newPassengerResponse(message: String, statue: Boolean) {

    }

    override suspend fun initToastMessage() {

    }

    override suspend fun initNewPassengerState() {

    }
}