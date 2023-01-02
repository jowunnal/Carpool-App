package com.mate.carpool.data.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.DTO.TicketDetailResponseDTO
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.CarpoolListRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeCarpoolListViewModel @Inject constructor(private val carpoolListRepository: CarpoolListRepositoryImpl) : ViewModel() {
    private val mutableCarpoolListState = MutableStateFlow<List<TicketListModel>>(listOf(
        TicketListModel()
    ))
    val carpoolListState get() = mutableCarpoolListState.asStateFlow()
    private val mutableCarpoolTicketState = MutableStateFlow<TicketModel>(
        TicketModel()
    )
    val carpoolTicketState get() = mutableCarpoolTicketState.asStateFlow()
    private val mutableCarpoolExistState = MutableStateFlow(false)
    val carpoolExistState get() = mutableCarpoolExistState.asStateFlow()

    fun getCarpoolList(){
        viewModelScope.launch {
            carpoolListRepository.getTicketList().collectLatest {
                when(it){
                    is List<*> ->{
                        mutableCarpoolListState.emit(it as List<TicketListModel>)
                    }
                    is ResponseMessage ->{
                    }
                    is Throwable ->{
                    }
                }
            }
        }
    }

    fun getCarpoolTicket(){
        viewModelScope.launch {
            carpoolListRepository.getTicket().collectLatest {
                when(it){
                    is TicketModel ->{
                        mutableCarpoolTicketState.emit(it)
                        mutableCarpoolExistState.emit(true)
                    }
                    is ResponseMessage ->{
                        mutableCarpoolExistState.emit(false)
                    }
                    is Throwable ->{
                        mutableCarpoolExistState.emit(false)
                    }
                }
            }
        }
    }
}