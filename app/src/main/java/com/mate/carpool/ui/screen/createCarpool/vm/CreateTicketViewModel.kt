package com.mate.carpool.ui.screen.createCarpool.vm

import com.mate.carpool.data.model.domain.StartArea
import com.mate.carpool.data.repository.TicketRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.screen.createCarpool.item.CreateTicketUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class CreateTicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
): BaseViewModel() {

    private val _uiState = MutableStateFlow(CreateTicketUiState.getInitValue())
    val uiState get() = _uiState.asStateFlow()

    fun setStartArea(area: String) = _uiState.update { state ->
        state.copy(startArea = StartArea.findByDisplayName(area), invalidArea = true)
    }

    val setBoardingPlace = fun (place: String) {
        _uiState.update { state ->
            state.copy(boardingPlace = place)
        }
    }

    fun setRecruitNumber(num: String) = _uiState.update { state ->
        state.copy(recruitNumber = num, invalidRecruitNumber = true)
    }

    val setOpenChatUrl = fun (url: String) {
        _uiState.update { state ->
            state.copy(openChatLink = url, invalidOpenChatLink = url.isNotBlank())
        }
    }

    val setBoardingFee = fun (fee: String) {
        _uiState.update { state ->
            val result = DecimalFormat("###,###").format(fee.replace(",","").toInt()).toString()
            state.copy(fee = result, invalidFee = true)
        }
    }

    val fetch = fun () {
        //TODO fetch api
        emitEvent(EVENT_CREATED_TICKET)
    }

    companion object {
        const val EVENT_CREATED_TICKET = "EVENT_CREATED_TICKET"
        const val EVENT_FAILED_CREATE_TICKET = "EVENT_FAILED_CREATE_TICKET"
    }

    /*
    fun createCarpoolTicket(){
        viewModelScope.launch {
            ticketStartDayMonth.update { mutableTicketModel.value?.startTime?.formatStartDayMonthToDTO()?:"" }
            ticketStartTime.update { mutableTicketModel.value?.startTime?.formatStartTimeToDTO()?:"" }
            apiService.postTicketNew(CreateCarpoolRequestDTO(ticketModel.value!!)).enqueue(object : Callback<ResponseMessage>{
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    when(response.code()){
                        200->{
                            Toast.makeText(context, "카풀 생성 성공!" , Toast.LENGTH_SHORT).show()
                        }
                        403->{
                            Toast.makeText(context,"카풀 생성 실패 : 드라이버만 카풀을 생성할 수 있습니다.", Toast.LENGTH_SHORT).show()
                        }
                        409->{
                            Toast.makeText(context,"카풀 생성 실패 : 이미 카풀을 생성하셨습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    Log.d("test","실패: "+ t.message)
                }

            })
        }
    }*/
}