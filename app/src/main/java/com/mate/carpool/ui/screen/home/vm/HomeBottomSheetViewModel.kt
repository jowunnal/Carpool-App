package com.mate.carpool.ui.screen.home.vm

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.TicketRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.screen.home.item.BottomSheetUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBottomSheetViewModel @Inject constructor(
    private val ticketChangeRepository:TicketRepository,
    private val carpoolListRepository: CarpoolListRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BottomSheetUiState.getInitValue())
    val uiState get() = _uiState.asStateFlow()

    fun getMyTicketDetail() {
        viewModelScope.launch {
            carpoolListRepository.getMyTicket().collectLatest {
                when(it){
                    is ApiResponse.Loading -> {
                    }
                    is ApiResponse.SuccessResponse -> {
                        _uiState.update { state ->
                            state.copy(ticket = it.responseMessage)
                        }
                    }
                    is ApiResponse.FailResponse -> {
                        emitSnackbar(SnackBarMessage(
                            headerMessage = "티켓정보를 가져오는데 실패했습니다. ${it.responseMessage.message}",
                            contentMessage = "다시 시도해 주세요."
                        ))
                    }
                    is ApiResponse.ExceptionResponse -> {
                        emitSnackbar(
                            SnackBarMessage(
                                headerMessage = "일시적인 장애가 발생하였습니다.",
                                contentMessage = "다시 시도해 주세요."
                            )
                        )
                    }
                }
            }
        }
    }

    fun getTicketDetail(id: Long) {
        carpoolListRepository.getTicket(id).onEach {
            when(it){
                is ApiResponse.Loading -> {}
                is ApiResponse.SuccessResponse -> {
                    _uiState.update { state ->
                        state.copy(ticket = it.responseMessage)
                    }
                }
                is ApiResponse.FailResponse -> {
                    when(it.responseMessage.code){
                        "404" -> {
                            emitSnackbar(
                                SnackBarMessage(
                                    headerMessage = "티켓을 불러오는데 실패했습니다.",
                                    contentMessage = "존재하지 않는 카풀입니다."
                                )
                            )
                        }
                        else -> {
                            emitSnackbar(
                                SnackBarMessage(
                                    headerMessage = "티켓을 불러오는데 실패했습니다.",
                                    contentMessage = it.responseMessage.message
                                )
                            )
                        }
                    }

                }
                is ApiResponse.ExceptionResponse -> {
                    emitSnackbar(
                        SnackBarMessage(
                            headerMessage = "일시적인 장애가 발생하였습니다.",
                            contentMessage = "다시 시도해 주세요."
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setPassengerId(id: Long) {
        _uiState.update { state ->
            state.copy(passengerId = id)
        }
    }

    fun setStudentId(id:String) {
        _uiState.update { state ->
            state.copy(studentId = id)
        }
    }


    fun addNewPassengerToTicket(ticketId: Long) {
        viewModelScope.launch {
            ticketChangeRepository.addNewPassengerToTicket(ticketId).collectLatest {
                when(it){
                    is ApiResponse.Loading -> {

                    }
                    is ApiResponse.SuccessResponse ->{
                        emitSnackbar(
                            SnackBarMessage(headerMessage = "티켓 탑승이 완료되었습니다."))
                    }
                    is ApiResponse.FailResponse -> {
                        when(it.responseMessage.code){
                            "400"-> {
                                emitSnackbar(SnackBarMessage(headerMessage = "자리가 만석이거나, 이미 탑승중입니다."))
                            }
                            "404"-> {
                                emitSnackbar(SnackBarMessage(headerMessage = "해당 카풀을 찾을 수 없습니다."))
                            }
                        }
                    }
                    else ->{
                        emitSnackbar(
                            SnackBarMessage(
                                headerMessage = "일시적인 장애가 발생하였습니다.",
                                contentMessage = "다시 시도해 주세요."
                            )
                        )
                    }
                }
            }
        }
    }

    fun updateTicketStatus(ticketId: Long, status: TicketStatus){
        viewModelScope.launch {
            ticketChangeRepository.updateTicketStatus(
                ticketId = ticketId,
                status = status
            ).collectLatest {
                when(it){
                    is ApiResponse.Loading -> {

                    }
                    is ApiResponse.SuccessResponse ->{

                    }
                    is ApiResponse.FailResponse -> {
                        when(it.responseMessage.code){
                            "403"->{
                                emitSnackbar(SnackBarMessage(headerMessage = "권한이 없습니다."))
                            }
                            "404"->{
                                emitSnackbar(SnackBarMessage(headerMessage = "존재하지 않는 카풀 입니다."))
                            }

                        }
                    }
                    else ->{
                        emitSnackbar(
                            SnackBarMessage(
                                headerMessage = "일시적인 장애가 발생하였습니다.",
                                contentMessage = "다시 시도해 주세요."
                            )
                        )
                    }
                }
            }
        }
    }

    fun deletePassengerToTicket(ticketId:Long) {
        viewModelScope.launch {
            ticketChangeRepository.deletePassengerToTicket(
                ticketId = ticketId,
                passengerId = uiState.value.passengerId
            ).collectLatest {
                when(it){
                    is ApiResponse.Loading -> {

                    }
                    is ApiResponse.SuccessResponse ->{
                        emitSnackbar(SnackBarMessage(headerMessage = "티켓 삭제가 완료되었습니다."))
                    }
                    is ApiResponse.FailResponse -> {
                        when(it.responseMessage.code){
                            "404"-> {
                                emitSnackbar(SnackBarMessage(headerMessage = "해당 티켓을 찾을 수 없습니다."))
                            }
                            else -> {
                                emitSnackbar(SnackBarMessage(headerMessage = "일시적인 장애가 발생하였습니다."))
                            }
                        }
                    }
                    is ApiResponse.ExceptionResponse ->{
                        emitSnackbar(
                            SnackBarMessage(
                                headerMessage = "일시적인 장애가 발생하였습니다.",
                                contentMessage = "다시 시도해 주세요."
                            )
                        )
                    }
                }
            }
        }
    }

    fun isTicketIsMineOrNot(ticketId: Long, ticketList:List<TicketListModel>) {
        if(ticketList.isNotEmpty()){
            for(item in ticketList){
                if(item.id==ticketId){
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

    fun getMyPassengerId(studentId:String): Long? {
        if(uiState.value.ticket.passenger != null)
            for(item in uiState.value.ticket.passenger!!){
                if(item.studentID == studentId)
                    return item.passengerId
            }
        return null
    }

    companion object{
        const val EVENT_ADDED_PASSENGER_TO_TICKET = "EVENT_ADDED_PASSENGER_TO_TICKET"
    }
}