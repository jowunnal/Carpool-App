package com.mate.carpool.ui.screen.ticketupdate

import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.screen.home.item.TicketState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TicketUpdateViewModel @Inject constructor() :BaseViewModel() {
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

    fun fetch() {

    }
}