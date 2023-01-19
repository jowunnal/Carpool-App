package com.mate.carpool.ui.screen.home.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object PreviewCarpoolListViewModel: ViewModel(),CarpoolListViewModelInterface {
    override val mutableCarpoolListState: MutableStateFlow<List<TicketListModel>> = MutableStateFlow(
        listOf(TicketListModel())
    )
    override val carpoolListState: StateFlow<List<TicketListModel>>
        get() = mutableCarpoolListState.asStateFlow()
    override val mutableCarpoolExistState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val carpoolExistState: StateFlow<Boolean>
        get() = mutableCarpoolExistState.asStateFlow()
    override val mutableMemberModelState: MutableStateFlow<MemberModel> = MutableStateFlow(
        MemberModel()
    )
    override val memberModelState: StateFlow<MemberModel>
        get() = mutableMemberModelState.asStateFlow()
    override val isRefreshState: MutableState<Boolean> = mutableStateOf(false)

    override fun getCarpoolList() {
    }

    override fun getMemberModel() {
    }

    override fun isTicketIsMineOrNot(id: Long): Boolean {
        return false
    }
}