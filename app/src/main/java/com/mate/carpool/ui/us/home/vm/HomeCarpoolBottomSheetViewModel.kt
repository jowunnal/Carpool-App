package com.mate.carpool.ui.us.home.vm

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.PassengerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeCarpoolBottomSheetViewModel @Inject constructor(
    private val passengerRepository:PassengerRepository,
    private val carpoolListRepository: CarpoolListRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    val mutableTicketId = MutableStateFlow(-1)
    val ticketId:StateFlow<Int> get() = mutableTicketId.asStateFlow()

    private val mutableNewPassengerStatue = MutableStateFlow(false)
    val newPassengerStatue get() = mutableNewPassengerStatue.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val carpoolTicketState:StateFlow<TicketModel> = ticketId.flatMapLatest {
        carpoolListRepository.getTicket(it)
    }.flowOn(Dispatchers.IO)
        .transform {ticket->
            if(ticket is TicketModel)
                emit(ticket)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = TicketModel()
        )

    fun addNewPassengerToTicket(id:Int){
        viewModelScope.launch {
            passengerRepository.addNewPassengerToTicket(id).collectLatest {
                when(it){
                    is ApiResponse.SuccessResponse ->{
                        newPassengerResponse("탑승이 완료되었습니다.",true)
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
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
        mutableNewPassengerStatue.emit(statue)
    }
}