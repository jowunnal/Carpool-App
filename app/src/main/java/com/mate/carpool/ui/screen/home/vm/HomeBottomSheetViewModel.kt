package com.mate.carpool.ui.screen.home.vm

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.dto.dto.request.UpdateTicketDTO
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.TicketRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.screen.home.item.BottomSheetUiState
import com.mate.carpool.ui.screen.home.item.TicketListState
import com.mate.carpool.ui.screen.home.item.TicketState
import com.mate.carpool.util.formatStartTimeToDTO
import com.mate.carpool.util.startTimeAsRequestDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeBottomSheetViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val carpoolListRepository: CarpoolListRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BottomSheetUiState.getInitValue())
    val uiState get() = _uiState.asStateFlow()

    fun setPassengerId(id: String) =
        _uiState.update { state ->
            state.copy(passengerId = id)
        }

    fun setUserId(id: String) =
        _uiState.update { state ->
            state.copy(userId = id)
        }

    fun getMyTicketDetail() = carpoolListRepository.getMyTicket()
        .onEach { ticketModel ->
            _uiState.update { state ->
                state.copy(ticket = ticketModel.asTicketState())
            }
        }.catch { e ->
            when (e) {
                is HttpException -> {
                    emitSnackbar(
                        SnackBarMessage(
                            headerMessage = "티켓정보를 가져오는데 실패했습니다.",
                            contentMessage = "다시 시도해 주세요."
                        )
                    )
                }
                else -> {
                    emitSnackbar(
                        SnackBarMessage(
                            headerMessage = "일시적인 장애가 발생하였습니다.",
                            contentMessage = "다시 시도해 주세요."
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)

    fun getTicketDetail(id: String) =
        carpoolListRepository.getTicketById(id).onEach { ticketModel ->
            _uiState.update { state ->
                state.copy(ticket = ticketModel.asTicketState())
            }

        }.catch { e ->
            when (e) {
                is HttpException -> {
                    emitSnackbar(
                        SnackBarMessage(
                            headerMessage = "티켓을 불러오는데 실패했습니다.",
                            contentMessage = e.message()
                        )
                    )
                }
                else -> {
                    emitSnackbar(
                        SnackBarMessage(
                            headerMessage = "일시적인 장애가 발생하였습니다.",
                            contentMessage = "다시 시도해 주세요."
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)


    fun addNewPassengerToTicket(ticketId: String) =
        ticketRepository.addNewPassengerToTicket(ticketId).onEach { response ->
            emitSnackbar(
                SnackBarMessage(headerMessage = "티켓 탑승이 완료되었습니다.")
            )
        }.catch { e ->
            when (e) {
                is HttpException -> {
                    when (e.code()) {
                        403 -> {
                            emitSnackbar(
                                SnackBarMessage(headerMessage = "드라이버는 카풀에 탑승할 수 없습니다.")
                            )
                        }
                        409 -> {
                            emitSnackbar(
                                SnackBarMessage(headerMessage = "자리가 만석이거나, 이미 탑승중입니다.")
                            )
                        }
                    }
                }
                else -> {
                    emitSnackbar(
                        SnackBarMessage(
                            headerMessage = "일시적인 장애가 발생하였습니다.",
                            contentMessage = "다시 시도해 주세요."
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)

    fun updateTicketDetail() =
        ticketRepository.updateTicketDetail(
            UpdateTicketDTO.fromDomain(
                id = uiState.value.ticket.id,
                startArea = uiState.value.ticket.startArea,
                startTime = uiState.value.ticket.startTime.startTimeAsRequestDTO(),
                endArea = uiState.value.ticket.endArea,
                boardingPlace = uiState.value.ticket.boardingPlace,
                openChatUrl = uiState.value.ticket.openChatUrl,
                recruitPerson = uiState.value.ticket.recruitPerson,
                ticketPrice = uiState.value.ticket.ticketPrice
            )
        ).onEach {
            emitSnackbar(
                SnackBarMessage(headerMessage = "티켓 수정이 완료되었습니다.")
            )
        }.catch {
            emitSnackbar(
                SnackBarMessage(
                    headerMessage = "일시적인 장애가 발생하였습니다.",
                    contentMessage = "다시 시도해 주세요."
                )
            )
        }.launchIn(viewModelScope)

    fun deletePassengerFromTicket(passengerId: String, role: MemberRole) =
        ticketRepository.deletePassengerToTicket(passengerId).onEach {
            when (role) {
                MemberRole.PASSENGER -> {
                    emitSnackbar(
                        SnackBarMessage(headerMessage = "성공적으로 하차가 완료되었습니다.")
                    )
                }
                MemberRole.DRIVER -> {
                    emitSnackbar(
                        SnackBarMessage(headerMessage = "성공적으로 퇴출이 완료되었습니다.")
                    )
                }
                else -> throw IllegalStateException("IllegalStateException role = [$role]")
            }
        }.catch { e ->
            when (e) {
                is HttpException -> {
                    when (e.code()) {
                        403 -> {
                            emitSnackbar(
                                SnackBarMessage(headerMessage = "해당 티켓에 대해 퇴출 권한이 없습니다.")
                            )
                        }
                    }
                }
                else -> {
                    emitSnackbar(
                        SnackBarMessage(
                            headerMessage = "일시적인 장애가 발생하였습니다.",
                            contentMessage = "다시 시도해 주세요."
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)

    fun deleteMyTicket(ticketId: String) =
        ticketRepository.deleteMyTicket(ticketId).onEach {
            emitSnackbar(
                SnackBarMessage(headerMessage = "티켓 삭제가 완료되었습니다.")
            )
        }.catch { e ->
            when (e) {
                is HttpException -> {
                    when (e.code()) {
                        403 -> {
                            emitSnackbar(
                                SnackBarMessage(headerMessage = "해당 티켓을 삭제할 권한이 없습니다.")
                            )
                        }
                    }
                }
                else -> {
                    emitSnackbar(
                        SnackBarMessage(
                            headerMessage = "일시적인 장애가 발생하였습니다.",
                            contentMessage = "다시 시도해 주세요."
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)


    fun isTicketIsMineOrNot(ticketId: String, ticketList: List<TicketListState>) {
        if (ticketList.isNotEmpty()) {
            for (item in ticketList) {
                if (item.id == ticketId) {
                    _uiState.update { state ->
                        state.copy(ticketIsMineOrNot = true)
                    }
                    return
                }
            }
        }
        _uiState.update { state ->
            state.copy(ticketIsMineOrNot = false)
        }
    }

    companion object {
        const val EVENT_ADDED_PASSENGER_TO_TICKET = "EVENT_ADDED_PASSENGER_TO_TICKET"
    }
}