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
import com.mate.carpool.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBottomSheetViewModel @Inject constructor(
    private val passengerRepository:PassengerRepository,
    private val carpoolListRepository: CarpoolListRepository
) : BaseViewModel(),HomeBottomSheetViewModelInterface {

    override val mutableTicketId = MutableStateFlow(-1L)
    override val ticketId:StateFlow<Long> get() = mutableTicketId.asStateFlow()

    private val mutableNewPassengerState = MutableStateFlow(false)
    override val newPassengerState get() = mutableNewPassengerState.asStateFlow()

    override val memberModel = MutableStateFlow(MemberModel())

    private val mutableToastMessage = MutableStateFlow("")
    override val toastMessage get() = mutableToastMessage.asStateFlow()

    override val initViewState = mutableStateOf(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val carpoolTicketState:StateFlow<TicketModel> = ticketId.flatMapLatest{ value->
        if(value == -1L)
            awaitCancellation()
        else
            carpoolListRepository.getTicket(value)
    }.transform {response->
        if(response is ApiResponse.SuccessResponse)
            emit(response.responseMessage)
        else if(response is ApiResponse.Loading)
            emit(TicketModel())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = TicketModel()
    )

    override fun addNewPassengerToTicket(id:Long){
        viewModelScope.launch {
            passengerRepository.addNewPassengerToTicket(id).collectLatest {
                when(it){
                    is ApiResponse.Loading -> {

                    }
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

    override suspend fun newPassengerResponse(message:String, statue:Boolean){
        mutableToastMessage.emit(message)
        mutableNewPassengerState.emit(statue)
    }

    override suspend fun initToastMessage() = mutableToastMessage.emit("")
    override suspend fun initNewPassengerState() = mutableNewPassengerState.emit(false)
}