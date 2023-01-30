package com.mate.carpool.ui.screen.home.compose

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.item.TicketType
import com.mate.carpool.ui.base.Event
import com.mate.carpool.ui.composable.SnackBarHost
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.screen.home.compose.component.*
import com.mate.carpool.ui.screen.home.vm.CarpoolListViewModel
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModel
import com.mate.carpool.ui.theme.MateTheme

@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeBottomSheetLayout(
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    onNavigateToReportView: (String) -> Unit,
    onNavigateToRegisterDriver: () -> Unit,
    homeCarpoolBottomSheetViewModel: HomeBottomSheetViewModel,
    carpoolListViewModel: CarpoolListViewModel
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val scaffoldState = rememberScaffoldState()

    val refreshState by carpoolListViewModel.refreshState.collectAsStateWithLifecycle()

    val userInfo by carpoolListViewModel.memberModelState.collectAsStateWithLifecycle()
    val carpoolExistState by carpoolListViewModel.carpoolExistState.collectAsStateWithLifecycle()
    val carpoolList by carpoolListViewModel.carpoolListState.collectAsStateWithLifecycle()

    val ticketDetail by homeCarpoolBottomSheetViewModel.carpoolTicketState.collectAsStateWithLifecycle()
    val event by carpoolListViewModel.event.collectAsStateWithLifecycle(
        initialValue = Event("EVENT_READY"),
        lifecycleOwner = LocalLifecycleOwner.current
    )
    val snackBarMessage by homeCarpoolBottomSheetViewModel.snackbarMessage.collectAsStateWithLifecycle(
        initialValue = "",
        lifecycleOwner = LocalLifecycleOwner.current
    )
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){}

    if(snackBarMessage.isNotBlank())
        LaunchedEffect(key1 = snackBarMessage){
            scaffoldState.snackbarHostState.showSnackbar(
                message = snackBarMessage,
                duration =  SnackbarDuration.Indefinite
            )
        }

    LaunchedEffect(key1 = event.type){
        when (event.type) {
            HomeBottomSheetViewModel.EVENT_ADDED_PASSENGER_TO_TICKET-> {
                bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {

        },
        snackbarHost = {
            Column() {
                SnackBarHost(
                    headerMessage = it.currentSnackbarData?.message?:"",
                    contentMessage = "",
                    snackBarHostState = scaffoldState.snackbarHostState,
                    disMissSnackBar = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() }
                )
                VerticalSpacer(height = 90.dp)
            }
        }
    ) {
        ModalBottomSheetLayout(
            sheetContent = {
                when(homeCarpoolBottomSheetViewModel::isTicketIsMineOrNot.invoke(userInfo.ticketList?: emptyList())){
                    true -> {
                        ReservationBottomSheetContent(
                            ticketDetail = ticketDetail,
                            userProfile = userInfo.user.profile,
                            userRole = userInfo.user.role,
                            userPassengerId = homeCarpoolBottomSheetViewModel::getMyPassengerId.invoke(userInfo.user.studentID),
                            onCloseBottomSheet = { bottomSheetState.animateTo(ModalBottomSheetValue.Hidden) },
                            onBrowseOpenChatLink = { launcher.launch(Intent(Intent.ACTION_VIEW, Uri.parse(ticketDetail.openChatUrl))) },
                            onNavigateToReportView = { onNavigateToReportView.invoke(homeCarpoolBottomSheetViewModel.studentId.value) },
                            onRefresh = carpoolListViewModel::onRefresh ,
                            setPassengerId = homeCarpoolBottomSheetViewModel::setPassengerId,
                            setStudentId = homeCarpoolBottomSheetViewModel::setStudentId,
                            deletePassengerFromTicket = homeCarpoolBottomSheetViewModel::deletePassengerToTicket,
                            updateTicketStatus = homeCarpoolBottomSheetViewModel::updateTicketStatus
                        )
                    }
                    false -> {
                        BottomSheetContent(
                            ticketDetail = ticketDetail,
                            userProfile = userInfo.user.profile,
                            userRole = userInfo.user.role,
                            onCloseBottomSheet = { bottomSheetState.animateTo(ModalBottomSheetValue.Hidden) },
                            addNewPassengerToTicket = homeCarpoolBottomSheetViewModel::addNewPassengerToTicket,
                            onRefresh = { carpoolListViewModel::onRefresh.invoke(HomeBottomSheetViewModel.EVENT_ADDED_PASSENGER_TO_TICKET) },
                            emitSnackBarMessage = homeCarpoolBottomSheetViewModel::emitSnackbar
                        )
                    }
                }
            },
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(it)
        ) {
            HomeView(
                refreshState = refreshState,
                carpoolExistState = carpoolExistState,
                carpoolList = carpoolList,
                userProfile = userInfo.user.profile,
                userRole = userInfo.user.role,
                setTicketId = homeCarpoolBottomSheetViewModel::setTicketId,
                setTicketIdFromMyTicket = carpoolListViewModel::getMyTicket,
                onNavigateToCreateCarpool = onNavigateToCreateCarpool,
                onNavigateToProfileView = onNavigateToProfileView,
                onNavigateToRegisterDriver = onNavigateToRegisterDriver,
                onRefresh = carpoolListViewModel::onRefresh,
                onOpenBottomSheet = { bottomSheetState.animateTo(ModalBottomSheetValue.Expanded) }
            )
        }
    }
}

@Composable
fun HomeView(
    refreshState: Boolean,
    carpoolExistState: Boolean,
    carpoolList: List<TicketListModel>,
    userProfile: String,
    userRole: MemberRole,
    setTicketId: (Long) -> Unit,
    setTicketIdFromMyTicket: ((Long) -> Unit) -> Unit,
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    onNavigateToRegisterDriver: () -> Unit,
    onRefresh: () -> Unit,
    onOpenBottomSheet: suspend () -> Unit
) {
    Column() {
        HomeAppBar(
            profileImage = userProfile,
            goToProfileScreen = onNavigateToProfileView
        )
        Column(
            Modifier
                .padding(start = 16.dp, end = 16.dp, top = 32.dp)
        ) {
            HomeMenu(
                userRole = userRole,
                onNavigateToCreateCarpool = onNavigateToCreateCarpool,
                onNavigateToRegisterDriver = onNavigateToRegisterDriver
            )

            TicketListHeader()

            Column(
                Modifier.weight(1f)
            ) {
                TicketList(
                    refreshState = refreshState,
                    carpoolList = carpoolList,
                    setTicketId = setTicketId,
                    onRefresh = onRefresh,
                    onOpenBottomSheet = onOpenBottomSheet
                )
            }

            MyCarpoolButton(
                carpoolExistState = carpoolExistState,
                setTicketIdFromMyTicket = setTicketIdFromMyTicket,
                setTicketId = setTicketId,
                onOpenBottomSheet = onOpenBottomSheet
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    MateTheme {
        HomeView(
            refreshState = false,
            carpoolExistState = true,
            carpoolList =
                listOf(
                    TicketListModel(
                        1,
                        "",
                        "인동",
                        25200L,
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
                        25200L,
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
                        25200L,
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
                        25200L,
                        3,
                        1,
                        TicketType.Free,
                        TicketStatus.Before,
                        DayStatus.Morning
                    )
                ),
            userProfile = "",
            userRole = MemberRole.Driver,
            setTicketId = {},
            setTicketIdFromMyTicket = {},
            onNavigateToCreateCarpool = {},
            onNavigateToProfileView = {},
            onRefresh = {},
            onOpenBottomSheet = {},
            onNavigateToRegisterDriver = {}
        )
    }
}
