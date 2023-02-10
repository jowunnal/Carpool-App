package com.mate.carpool.ui.screen.ticketupdate

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.dto.dto.request.UpdateTicketDTO
import com.mate.carpool.data.repository.TicketRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.screen.home.item.TicketState
import com.mate.carpool.util.startTimeAsRequestDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TicketUpdateViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) :BaseViewModel() {
    private val _uiState = MutableStateFlow(TicketState.getInitValue())
    val uiState get() = _uiState.asStateFlow()

    fun setStartArea(area: String) =
        _uiState.update { state ->
            state.copy(startArea = area)
        }

    fun setBoardingPlace(place: String) =
        _uiState.update { state ->
            state.copy(boardingPlace = place)
        }

    fun setStartTime(time: Long) =
        _uiState.update { state ->
            state.copy(startTime = time)
        }

    fun setOpenChatLink(link: String) =
        _uiState.update { state ->
            state.copy(openChatUrl = link)
        }

    fun setRecruitPersonCount(num: String) =
        _uiState.update { state ->
            state.copy(recruitPerson = num.toInt())
        }

    fun setFee(fee: String) =
        _uiState.update { state ->
            state.copy(ticketPrice = fee.replace(",","").toInt())
        }

    fun updateTicket() =
        ticketRepository.updateTicketDetail(
            UpdateTicketDTO.fromDomain(
                id = uiState.value.id,
                startArea = uiState.value.startArea,
                startTime = uiState.value.startTime.startTimeAsRequestDTO(),
                endArea = uiState.value.endArea,
                boardingPlace = uiState.value.boardingPlace,
                openChatUrl = uiState.value.openChatUrl,
                recruitPerson = uiState.value.recruitPerson,
                ticketPrice = uiState.value.ticketPrice
            )
        ).onEach {
            emitEvent(EVENT_UPDATED_TICKET)
        }.catch {
            emitSnackbar(
                SnackBarMessage(
                    headerMessage = "일시적인 장애가 발생하였습니다.",
                    contentMessage = "다시 시도해 주세요."
                )
            )
        }.launchIn(viewModelScope)

    companion object {
        const val EVENT_UPDATED_TICKET = "EVENT_UPDATED_TICKET"
    }
}