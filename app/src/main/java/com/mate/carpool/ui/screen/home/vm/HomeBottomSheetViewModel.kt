package com.mate.carpool.ui.screen.home.vm

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.PassengerRepository
import com.mate.carpool.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBottomSheetViewModel @Inject constructor(
    private val passengerRepository:PassengerRepository,
    private val carpoolListRepository: CarpoolListRepository
) : BaseViewModel() {

    private val mutableTicketId = MutableStateFlow(-1L)
    private val ticketId:StateFlow<Long> get() = mutableTicketId.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val carpoolTicketState:StateFlow<TicketModel> = ticketId.flatMapLatest{ value->
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

    fun setTicketId(id: Long) {
        mutableTicketId.value = id
    }

    fun addNewPassengerToTicket(id:Long){
        viewModelScope.launch {
            passengerRepository.addNewPassengerToTicket(id).collectLatest {
                when(it){
                    is ApiResponse.Loading -> {

                    }
                    is ApiResponse.SuccessResponse ->{
                        emitSnackbar("")
                    }
                    is ApiResponse.FailResponse -> {
                        when(it.responseMessage.code){
                            "400"-> {
                                emitSnackbar("자리가 만석이거나, 이미 탑승중입니다.")
                            }
                            "404"-> {
                                emitSnackbar("해당 카풀을 찾을 수 없습니다.")
                            }
                        }
                    }
                    else ->{
                        emitSnackbar("일시적인 장애가 발생하였습니다. 재시도 해주세요.")
                    }
                }
            }
        }
    }
}