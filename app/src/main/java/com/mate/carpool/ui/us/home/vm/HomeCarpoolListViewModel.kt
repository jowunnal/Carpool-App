package com.mate.carpool.ui.us.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.MemberRole
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.repository.CarpoolListRepositoryImpl
import com.mate.carpool.data.repository.MemberRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeCarpoolListViewModel @Inject constructor(
    private val carpoolListRepository: CarpoolListRepositoryImpl,
    private val memberRepository: MemberRepositoryImpl
    ) : ViewModel()
{
    private val mutableCarpoolListState = MutableStateFlow<List<TicketListModel>>(listOf(
        TicketListModel()
    ))
    val carpoolListState get() = mutableCarpoolListState.asStateFlow()

    private val mutableCarpoolTicketState = MutableStateFlow<TicketModel>(TicketModel())
    val carpoolTicketState get() = mutableCarpoolTicketState.asStateFlow()

    private val mutableCarpoolExistState = MutableStateFlow(false)
    val carpoolExistState get() = mutableCarpoolExistState.asStateFlow()

    private val mutableMemberRoleState = MutableStateFlow(MemberRole())
    val memberRoleState get() = mutableMemberRoleState.asStateFlow()

    init {
        getCarpoolList()
        getMemberRole()
    }

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

    suspend fun getCarpoolTicket(id:Int){
        viewModelScope.launch {
            carpoolListRepository.getTicket(id).collectLatest {
                when(it){
                    is TicketModel ->{
                        mutableCarpoolTicketState.emit(it)
                    }
                    is ResponseMessage ->{
                    }
                    is Throwable ->{
                    }
                }
            }
        }
    }

    fun getMemberRole(){
        viewModelScope.launch {
            memberRepository.getMemberRole().collectLatest {
                when(it){
                    is MemberRole ->{
                        mutableMemberRoleState.emit(it)
                        if(it.ticketList?.size!=0)
                            mutableCarpoolExistState.emit(true)
                        else
                            mutableCarpoolExistState.emit(false)
                    }
                    is ResponseMessage ->{
                    }
                    is Throwable ->{
                    }
                }
            }
        }
    }

    fun isTicketIsMineOrNot(id:Int) : Boolean{
        if(memberRoleState.value.ticketList!=null){
            for(item in memberRoleState.value.ticketList!!){
                if(item.id==id){
                    return true
                }
            }
        }
        return false
    }
}