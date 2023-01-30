package com.mate.carpool.ui.screen.home.vm

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.TicketChangeRepository
import com.mate.carpool.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBottomSheetViewModel @Inject constructor(
    private val ticketChangeRepository:TicketChangeRepository,
    private val carpoolListRepository: CarpoolListRepository
) : BaseViewModel() {

    private val mutableTicketId = MutableStateFlow(-1L)
    private val ticketId:StateFlow<Long> get() = mutableTicketId.asStateFlow()
    val passengerId = MutableStateFlow(0L)
    val studentId = MutableStateFlow("")

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
            emit(TicketModel.getInitValue())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = TicketModel.getInitValue()
    )

    fun setTicketId(id: Long) {
        mutableTicketId.update { id }
    }

    fun setPassengerId(id: Long) {
        passengerId.update { id }
    }

    fun setStudentId(id:String) {
        studentId.update { id }
    }

    fun addNewPassengerToTicket(){
        viewModelScope.launch {
            ticketChangeRepository.addNewPassengerToTicket(ticketId.value).collectLatest {
                when(it){
                    is ApiResponse.Loading -> {

                    }
                    is ApiResponse.SuccessResponse ->{
                        emitSnackbar("티켓 탑승이 완료되었습니다.")
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

    fun updateTicketStatus(status: TicketStatus){
        viewModelScope.launch {
            ticketChangeRepository.updateTicketStatus(
                ticketId = ticketId.value,
                status = status
            ).collectLatest {
                when(it){
                    is ApiResponse.Loading -> {

                    }
                    is ApiResponse.SuccessResponse ->{
                        emitSnackbar("")
                    }
                    is ApiResponse.FailResponse -> {
                        when(it.responseMessage.code){
                            "403"->{
                                emitSnackbar("권한이 없습니다.")
                            }
                            "404"->{
                                emitSnackbar("존재하지 않는 카풀 입니다.")
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

    fun deletePassengerToTicket(){
        viewModelScope.launch {
            ticketChangeRepository.deletePassengerToTicket(
                ticketId = ticketId.value,
                passengerId = passengerId.value
            ).collectLatest {
                when(it){
                    is ApiResponse.Loading -> {
                        emitSnackbar("")
                    }
                    is ApiResponse.SuccessResponse ->{
                        emitSnackbar("티켓 삭제가 완료되었습니다.")
                    }
                    is ApiResponse.FailResponse -> {
                        when(it.responseMessage.code){
                            "404"-> emitSnackbar("해당 티켓을 찾을 수 없습니다.")
                            else -> {emitSnackbar(it.responseMessage.message)}
                        }
                    }
                    is ApiResponse.ExceptionResponse ->{
                        emitSnackbar("일시적인 장애가 발생하였습니다. 재시도 해주세요. ${it.e.message}")
                    }
                }
            }
        }
    }

    fun isTicketIsMineOrNot(ticketList:List<TicketListModel>) : Boolean{
        if(ticketList.isNotEmpty()){
            for(item in ticketList){
                if(item.id==ticketId.value){
                    return true
                }
            }
        }
        return false
    }

    fun getMyPassengerId(studentId:String): Long? {
        if(carpoolTicketState.value.passenger != null)
            for(item in carpoolTicketState.value.passenger!!){
                if(item.studentID == studentId)
                    return item.passengerId
            }
        return null
    }

    companion object{
        const val EVENT_ADDED_PASSENGER_TO_TICKET = "EVENT_ADDED_PASSENGER_TO_TICKET"
    }
}