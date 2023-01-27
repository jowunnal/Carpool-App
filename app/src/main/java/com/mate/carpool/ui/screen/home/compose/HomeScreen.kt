package com.mate.carpool.ui.screen.home.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.item.DayStatus
import com.mate.carpool.data.model.domain.item.MemberRole
import com.mate.carpool.data.model.domain.item.TicketStatus
import com.mate.carpool.data.model.domain.item.TicketType
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.PrimaryButton
import com.mate.carpool.ui.screen.home.compose.component.*
import com.mate.carpool.ui.screen.home.vm.*
import com.mate.carpool.ui.screen.reserveDriver.fragment.ReserveDriverFragment
import com.mate.carpool.ui.screen.reservePassenger.ReservePassengerFragment
import com.mate.carpool.ui.theme.MateTheme
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeBottomSheetLayout(
    fragmentManager: FragmentManager,
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    homeCarpoolBottomSheetViewModel: HomeBottomSheetViewModel,
    carpoolListViewModel: CarpoolListViewModel
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val initViewState = rememberSaveable {
        mutableStateOf(false)
    }

    val userInfo by carpoolListViewModel.memberModelState.collectAsStateWithLifecycle()
    val carpoolExistState by carpoolListViewModel.carpoolExistState.collectAsStateWithLifecycle()
    val carpoolList by carpoolListViewModel.carpoolListState.collectAsStateWithLifecycle()

    val ticketDetail by homeCarpoolBottomSheetViewModel.carpoolTicketState.collectAsStateWithLifecycle()
    val snackBarMessage by homeCarpoolBottomSheetViewModel.snackbarMessage.collectAsStateWithLifecycle(
        initialValue = "",
        lifecycleOwner = LocalLifecycleOwner.current
    )

    if(snackBarMessage.isNotBlank())
        LaunchedEffect(key1 = snackBarMessage){

        }

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent(
                bottomSheetState = bottomSheetState,
                ticketDetail = ticketDetail,
                fragmentManager = fragmentManager,
                userProfile = userInfo.user.profile,
                userRole = userInfo.user.role,
                userStudentID = userInfo.user.studentID,
                addNewPassengerToTicket = homeCarpoolBottomSheetViewModel::addNewPassengerToTicket,
                reNewHomeListener = { initViewState.value = false },
            )
        },
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(20.dp)
    ) {
        HomeView(
            bottomSheetState = bottomSheetState,
            initViewState = initViewState,
            fragmentManager = fragmentManager,
            carpoolExistState = carpoolExistState,
            carpoolList = carpoolList,
            userProfile = userInfo.user.profile,
            userRole = userInfo.user.role,
            userStudentID = userInfo.user.studentID,
            isTicketIsMineOrNot = carpoolListViewModel::isTicketIsMineOrNot,
            getMemberModel = carpoolListViewModel::getCarpoolList,
            getCarpoolList = carpoolListViewModel::getMemberModel,
            setTicketId = homeCarpoolBottomSheetViewModel::setTicketId,
            onNavigateToCreateCarpool = onNavigateToCreateCarpool,
            onNavigateToProfileView = onNavigateToProfileView,
            reNewHomeListener = { initViewState.value = false }
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    bottomSheetState: ModalBottomSheetState,
    initViewState: MutableState<Boolean>,
    fragmentManager: FragmentManager,
    carpoolExistState: Boolean,
    carpoolList: List<TicketListModel>,
    userProfile: String,
    userRole: MemberRole,
    userStudentID: String,
    isTicketIsMineOrNot: (Long) -> Boolean,
    getMemberModel: () -> Unit,
    getCarpoolList: () -> Unit,
    setTicketId: (Long) -> Unit,
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    reNewHomeListener: () -> Unit
) {

    if (initViewState.value) {
        LaunchedEffect(key1 = initViewState.value) {
            getMemberModel()
            getCarpoolList()
            initViewState.value = false
        }
    }

    Scaffold(
        topBar = {
            HomeAppBar(
                profileImage = userProfile,
                goToProfileScreen = onNavigateToProfileView
            )
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(start = 16.dp, end = 16.dp, top = 32.dp)
        ) {
            HomeMenu(
                userRole = userRole,
                onNavigateToCreateCarpool = onNavigateToCreateCarpool
            )

            TicketHeader()

            Column(
                Modifier.weight(1f)
            ) {
                TicketList(
                    bottomSheetState = bottomSheetState,
                    userRole = userRole,
                    userStudentID = userStudentID,
                    initViewState = initViewState,
                    fragmentManager = fragmentManager,
                    carpoolList = carpoolList,
                    setTicketId = setTicketId,
                    reNewHomeListener = reNewHomeListener,
                    isTicketMineOrNot = isTicketIsMineOrNot
                )
            }

            MyCarpoolButton(
                carpoolExistState = carpoolExistState,
                userRole = userRole,
                fragmentManager = fragmentManager,
                userStudentID = userStudentID,
                reNewHomeListener = reNewHomeListener
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    MateTheme {
        HomeView(
            bottomSheetState = ModalBottomSheetState(ModalBottomSheetValue.Hidden),
            initViewState = remember{ mutableStateOf(false) },
            fragmentManager = object : FragmentManager() {},
            carpoolExistState = false,
            carpoolList =
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
                ),
            userProfile = "",
            userRole = MemberRole.Driver,
            userStudentID = "",
            isTicketIsMineOrNot = {false},
            getMemberModel = {},
            getCarpoolList = {},
            setTicketId = {},
            onNavigateToCreateCarpool = {},
            onNavigateToProfileView = {},
            reNewHomeListener = {}
        )
    }
}
