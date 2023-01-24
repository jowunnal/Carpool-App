package com.mate.carpool.ui.screen.home.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.domain.item.DayStatus
import com.mate.carpool.data.model.domain.item.MemberRole
import com.mate.carpool.data.model.domain.item.TicketStatus
import com.mate.carpool.data.model.domain.item.TicketType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object PreviewCarpoolListViewModel: ViewModel(),CarpoolListViewModelInterface {

    override val carpoolListState: StateFlow<List<TicketListModel>>
        get() = MutableStateFlow<List<TicketListModel>>(
            listOf(
                TicketListModel(
                1,
                "",
                "인동",
                "08:00",
                3,
                1,
                TicketType.Cost,
                TicketStatus.Before,
                DayStatus.Morning
                ),
                TicketListModel(
                    2,
                    "",
                    "인동",
                    "07:00",
                    3,
                    1,
                    TicketType.Cost,
                    TicketStatus.Before,
                    DayStatus.Morning
                ),
                TicketListModel(
                    3,
                    "",
                    "인동",
                    "08:00",
                    3,
                    1,
                    TicketType.Free,
                    TicketStatus.Before,
                    DayStatus.Morning
                ),
                TicketListModel(
                    4,
                    "",
                    "인동",
                    "09:00",
                    3,
                    1,
                    TicketType.Free,
                    TicketStatus.Before,
                    DayStatus.Morning
                )
            )
        ).asStateFlow()

    override val carpoolExistState: StateFlow<Boolean>
        get() = MutableStateFlow(true).asStateFlow()

    override val memberModelState: StateFlow<MemberModel>
        get() = MutableStateFlow(
            MemberModel(
                UserModel(
                    "진호",
                    "20173000",
                    "동의대학교",
                    "010-1234-5678",
                    MemberRole.Driver,
                    "",
                    emptyList()
                ),
                listOf(TicketListModel(
                    1,
                    "",
                    "인동",
                    "08:00",
                    3,
                    1,
                    TicketType.Cost,
                    TicketStatus.Before,
                    DayStatus.Morning
                )))
        ).asStateFlow()

    override val isRefreshState: MutableState<Boolean> = mutableStateOf(false)

    override fun getCarpoolList() {
    }

    override fun getMemberModel() {
    }

    override fun isTicketIsMineOrNot(id: Long): Boolean {
        return false
    }
}