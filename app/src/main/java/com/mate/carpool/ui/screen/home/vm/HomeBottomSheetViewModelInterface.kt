package com.mate.carpool.ui.screen.home.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface HomeBottomSheetViewModelInterface {
    val mutableTicketId:MutableStateFlow<Long>
    val ticketId: StateFlow<Long>
    val newPassengerState : StateFlow<Boolean>
    val memberModel : MutableStateFlow<MemberModel>
    val toastMessage : StateFlow<String>
    val initViewState : MutableState<Boolean>
    val carpoolTicketState: StateFlow<TicketModel>

    fun addNewPassengerToTicket(id:Long)
    suspend fun newPassengerResponse(message:String,statue:Boolean)
    suspend fun initToastMessage()
    suspend fun initNewPassengerState()
}