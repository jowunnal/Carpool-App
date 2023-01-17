package com.mate.carpool.ui.screen.home.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.PassengerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBottomSheetViewModel @Inject constructor(
    private val passengerRepository:PassengerRepository,
    private val carpoolListRepository: CarpoolListRepository
) : ViewModel() {

    val mutableTicketId = MutableStateFlow(-1L)
    val ticketId:StateFlow<Long> get() = mutableTicketId.asStateFlow()

    private val mutableNewPassengerState = MutableStateFlow(false)
    val newPassengerState get() = mutableNewPassengerState.asStateFlow()

    val memberModel = MutableStateFlow(MemberModel())

    private val mutableToastMessage = MutableStateFlow("")
    val toastMessage get() = mutableToastMessage.asStateFlow()

    val initViewState = mutableStateOf(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val carpoolTicketState:StateFlow<TicketModel> = ticketId.flatMapLatest{
        carpoolListRepository.getTicket(it)
    }.flowOn(Dispatchers.IO)
        .transform {response->
            if(response is ApiResponse.SuccessResponse)
                emit(response.responseMessage)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = TicketModel()
        )

    fun addNewPassengerToTicket(id:Long){
        viewModelScope.launch {
            passengerRepository.addNewPassengerToTicket(id).collectLatest {
                when(it){
                    is ApiResponse.SuccessResponse ->{
                        newPassengerResponse("",true)
                    }
                    is ApiResponse.FailResponse -> {
                        when(it.responseMessage.code){
                            "400"-> {
                                newPassengerResponse("자리가 만석이거나, 이미 탑승중입니다.",false)
                            }
                            "404"-> {
                                newPassengerResponse("해당 카풀을 찾을 수 없습니다.",false)
                            }
                        }
                    }
                    else ->{
                        newPassengerResponse("일시적인 장애가 발생하였습니다. 재시도 해주세요.",false)
                    }
                }
            }
        }
    }

    private suspend fun newPassengerResponse(message:String,statue:Boolean){
        mutableToastMessage.emit(message)
        mutableNewPassengerState.emit(statue)
    }

    suspend fun initToastMessage() = mutableToastMessage.emit("")
    suspend fun initNewPassengerState() = mutableNewPassengerState.emit(false)
}