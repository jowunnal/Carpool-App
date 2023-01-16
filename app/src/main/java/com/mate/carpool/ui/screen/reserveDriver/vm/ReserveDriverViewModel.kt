package com.mate.carpool.ui.screen.reserveDriver.vm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.PassengerRepository
import com.mate.carpool.data.service.APIService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ReserveDriverViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: APIService,
    private val carpoolListRepository: CarpoolListRepository,
    private val passengerRepository: PassengerRepository
) :ViewModel() {

    var passengerCount = MutableLiveData<String>()
    val ticketID = MutableStateFlow(0L)
    val passengerId = MutableStateFlow(0L)

    private val mutableToastMessage = MutableStateFlow("")
    val toastMessage get() = mutableToastMessage.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val ticketDetail : StateFlow<TicketModel> = ticketID.flatMapLatest {
        carpoolListRepository.getTicket(it)
    }.transform{
        if(it is ApiResponse.SuccessResponse)
            emit(it.responseMessage)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = TicketModel()
    )

    init {
        getMyTicket()
    }

    fun getMyTicket(){
        viewModelScope.launch {
            carpoolListRepository.getMyTicket().collectLatest {
                when(it){
                    is ApiResponse.SuccessResponse -> {
                        ticketID.value = it.responseMessage.id
                    }
                    is ApiResponse.FailResponse -> {
                        mutableToastMessage.emit("티켓정보를 가져오는데 실패했습니다. ${it.responseMessage.message}")
                    }
                    is ApiResponse.ExceptionResponse -> {
                        mutableToastMessage.emit("일시적인 장애가 발생하였습니다.")
                    }
                }
            }
        }
    }

    fun updateTicketStatus(status:String){
        viewModelScope.launch {
            apiService.getTicketUpdateId(ticketID.value,status).enqueue(object : Callback<ResponseMessage>{
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    when(response.code()){
                        200->{
                            Toast.makeText(context,"변경이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                        403->{
                            Toast.makeText(context,"권한이 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                        404->{
                            Toast.makeText(context,"존재하지 않는 카풀 입니다.", Toast.LENGTH_SHORT).show()
                        }
                        /*
                        200->{
                            mutableToastMessage.emit("변경이 완료되었습니다.")
                        }
                        403->{
                            mutableToastMessage.emit("권한이 없습니다.")
                        }
                        404->{
                            mutableToastMessage.emit("존재하지 않는 카풀 입니다.")
                        }*/
                    }
                }

                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    Log.d("test","실패: "+ t.message)
                }

            })
        }
    }

    fun deletePassengerToTicket(){
        viewModelScope.launch {
            passengerRepository.deletePassengerToTicket(ticketID.value,passengerId.value).collectLatest {
                Toast.makeText(context,it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setTicketPassengerId(
        studentNumber:String
    ){
        ticketDetail.value.passenger?.forEach {
            if(it.studentID==studentNumber) {
                passengerId.value = it.passengerId
            }
        }
    }
}