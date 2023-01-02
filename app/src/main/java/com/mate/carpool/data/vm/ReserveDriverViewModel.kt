package com.mate.carpool.data.vm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.model.DTO.TicketDetailResponseDTO
import com.mate.carpool.data.service.APIService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ReserveDriverViewModel @Inject constructor(@ApplicationContext private val context: Context, private val apiService: APIService) :ViewModel(){
    private val mutableTicketDetail = MutableLiveData<TicketDetailResponseDTO>()
    val ticketDetail:LiveData<TicketDetailResponseDTO> get() = mutableTicketDetail
    var passengerCount = MutableLiveData<String>()
    val ticketID=MutableLiveData(504)

    fun getTicketDetailFromId(){
        apiService.getTicketReadId(ticketID.value!!).enqueue(object : Callback<TicketDetailResponseDTO>{
            override fun onResponse(
                call: Call<TicketDetailResponseDTO>,
                response: Response<TicketDetailResponseDTO>
            ) {
                when(response.code()){
                    200->{
                        mutableTicketDetail.value=response.body()
                        when(mutableTicketDetail.value?.ticketType){
                            "COST"-> mutableTicketDetail.value?.ticketType="유료"
                            "FREE"-> mutableTicketDetail.value?.ticketType="무료"
                        }

                        passengerCount.value = "패신져 ("+(mutableTicketDetail.value?.passengers?.size!!).toString()+"/3)"
                    }
                    404->{
                        Toast.makeText(context,"존재하지 않는 카풀 입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<TicketDetailResponseDTO>, t: Throwable) {
                Log.d("test","실패: "+ t.message)
            }

        })
    }

    fun updateTicketStatus(status:String){
        viewModelScope.launch {
            apiService.getTicketUpdateId(ticketID.value!!,status).enqueue(object : Callback<ResponseMessage>{
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
                    }
                }

                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    Log.d("test","실패: "+ t.message)
                }

            })
        }
    }
}