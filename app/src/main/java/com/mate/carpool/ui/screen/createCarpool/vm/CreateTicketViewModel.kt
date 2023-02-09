package com.mate.carpool.ui.screen.createCarpool.vm

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.repository.TicketRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.screen.createCarpool.item.CreateTicketUiState
import com.mate.carpool.ui.screen.createCarpool.item.TimeUiState
import com.mate.carpool.util.asStartTimeDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class CreateTicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CreateTicketUiState.getInitValue())
    val uiState get() = _uiState.asStateFlow()

    val setStartArea = fun(area: String) {
        _uiState.update { state ->
            state.copy(startArea = area, invalidArea = area.isNotBlank())
        }
    }

    val setBoardingPlace = fun(place: String) {
        _uiState.update { state ->
            state.copy(boardingPlace = place)
        }
    }

    val setEndArea = fun(endArea: String) {
        _uiState.update { state ->
            state.copy(endArea = endArea)
        }
    }

    fun setStartTime(timeUiState: TimeUiState) = _uiState.update { state ->
        state.copy(
            startTime = String.format("%02d:%02d", timeUiState.hour, timeUiState.min),
            invalidStartTime = true
        )
    }

    fun setRecruitNumber(num: String) = _uiState.update { state ->
        state.copy(recruitNumber = num, invalidRecruitNumber = num.isNotBlank())
    }

    val setOpenChatUrl = fun(url: String) {
        _uiState.update { state ->
            state.copy(openChatLink = url, invalidOpenChatLink = url.isNotBlank())
        }
    }

    val setBoardingFee = fun(fee: String) {
        _uiState.update { state ->
            val unFormattedFee = fee.replace(",", "").toInt()
            val result = DecimalFormat("###,###").format(unFormattedFee)
            state.copy(fee = result, invalidFee = fee.isNotBlank())
        }
    }

    fun fetch() {
        ticketRepository.createTicket(
            startArea = uiState.value.startArea,
            startTime = uiState.value.startTime.asStartTimeDomain(),
            endArea = uiState.value.endArea,
            boardingPlace = uiState.value.boardingPlace,
            openChatUrl = uiState.value.openChatLink,
            recruitPerson = uiState.value.recruitNumber.replace(",", "").toInt(),
            ticketPrice = uiState.value.fee.toInt()
        ).onEach {
            emitEvent(EVENT_CREATED_TICKET)
        }.catch { e ->
            when(e) {
                is HttpException -> {
                    when(e.code()) {
                        409 -> {
                            emitEvent(EVENT_FAILED_CREATE_TICKET_ALREADY_CREATED)
                        }
                    }
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
        const val EVENT_FAILED_CREATE_TICKET_ALREADY_CREATED = "EVENT_FAILED_CREATE_TICKET_ALREADY_CREATED"
    }
}