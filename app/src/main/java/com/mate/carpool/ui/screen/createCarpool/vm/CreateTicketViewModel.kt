package com.mate.carpool.ui.screen.createCarpool.vm

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.StartArea
import com.mate.carpool.data.repository.TicketRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.screen.createCarpool.item.CreateTicketUiState
import com.mate.carpool.ui.screen.createCarpool.item.TimeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class CreateTicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
): BaseViewModel() {

    private val _uiState = MutableStateFlow(CreateTicketUiState.getInitValue())
    val uiState get() = _uiState.asStateFlow()

    fun setStartArea(area: String) = _uiState.update { state ->
        state.copy(startArea = StartArea.findByDisplayName(area), invalidArea = area.isNotBlank())
    }

    val setBoardingPlace = fun (place: String) {
        _uiState.update { state ->
            state.copy(boardingPlace = place)
        }
    }

    fun setStartTime(timeUiState: TimeUiState) = _uiState.update { state ->
        state.copy(startTime = String.format("%02d:%02d",timeUiState.hour,timeUiState.min), invalidStartTime = true)
    }

    fun setRecruitNumber(num: String) = _uiState.update { state ->
        state.copy(recruitNumber = num, invalidRecruitNumber = num.isNotBlank())
    }

    val setOpenChatUrl = fun (url: String) {
        _uiState.update { state ->
            state.copy(openChatLink = url, invalidOpenChatLink = url.isNotBlank())
        }
    }

    val setBoardingFee = fun (fee: String) {
        _uiState.update { state ->
            val unFormattedFee = fee.replace(",","").toInt()
            val result = DecimalFormat("###,###").format(unFormattedFee)
            state.copy(fee = result, invalidFee = fee.isNotBlank())
        }
    }

    val fetch = fun () {
        ticketRepository.createTicket(uiState.value.asTicketDomainModel()).onEach { response ->
            when(response.status) {
                "CREATED" -> {
                    emitEvent(EVENT_CREATED_TICKET)
                }
                else -> {
                    emitEvent(EVENT_FAILED_CREATE_TICKET)
                }
            }
        }.launchIn(viewModelScope)
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