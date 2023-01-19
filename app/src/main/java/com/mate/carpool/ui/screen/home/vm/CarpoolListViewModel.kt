package com.mate.carpool.ui.screen.home.vm

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.MemberRepository
import com.mate.carpool.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarpoolListViewModel @Inject constructor(
    private val carpoolListRepository: CarpoolListRepository,
    private val memberRepository: MemberRepository
    ) : BaseViewModel(),CarpoolListViewModelInterface
{
    private val mutableCarpoolListState = MutableStateFlow<List<TicketListModel>>(emptyList())
    override val carpoolListState get() = mutableCarpoolListState.asStateFlow()

    private val mutableCarpoolExistState = MutableStateFlow(false)
    override val carpoolExistState get() = mutableCarpoolExistState.asStateFlow()

    private val mutableMemberModelState = MutableStateFlow(MemberModel())
    override val memberModelState get() = mutableMemberModelState.asStateFlow()

    override val isRefreshState = mutableStateOf(false)

    override fun getCarpoolList(){
        viewModelScope.launch {
            carpoolListRepository.getTicketList().collectLatest {
                when(it){
                    is ApiResponse.SuccessResponse ->{
                        mutableCarpoolListState.emit(it.responseMessage)
                    }
                    is ApiResponse.FailResponse ->{
                    }
                    is ApiResponse.ExceptionResponse ->{
                    }
                }
            }
        }
    }

    override fun getMemberModel(){
        viewModelScope.launch {
            memberRepository.getMemberInfo().collectLatest {
                when(it){
                    is ApiResponse.SuccessResponse ->{
                        mutableMemberModelState.emit(it.responseMessage)
                        if(it.responseMessage.ticketList?.size!=0)
                            mutableCarpoolExistState.emit(true)
                        else
                            mutableCarpoolExistState.emit(false)
                    }
                    is ApiResponse.FailResponse ->{
                    }
                    is ApiResponse.ExceptionResponse ->{
                    }
                }
            }
        }
    }

    override fun isTicketIsMineOrNot(id:Long) : Boolean{
        if(memberModelState.value.ticketList!=null){
            for(item in memberModelState.value.ticketList!!){
                if(item.id==id){
                    return true
                }
            }
        }
        return false
    }
}