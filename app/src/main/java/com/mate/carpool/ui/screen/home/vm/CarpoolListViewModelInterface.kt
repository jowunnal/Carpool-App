package com.mate.carpool.ui.screen.home.vm

import androidx.compose.runtime.MutableState
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface CarpoolListViewModelInterface {

    val carpoolListState :StateFlow<List<TicketListModel>>
    val carpoolExistState :StateFlow<Boolean>
    val memberModelState :StateFlow<MemberModel>
    val isRefreshState : MutableState<Boolean>

    fun getCarpoolList():Unit
    fun getMemberModel():Unit
    fun isTicketIsMineOrNot(id:Long) : Boolean
}