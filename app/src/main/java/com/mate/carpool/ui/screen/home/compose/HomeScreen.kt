package com.mate.carpool.ui.screen.home.compose

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.item.TicketType
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.base.Event
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.composable.SnackBarHostCustom
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.screen.home.compose.component.*
import com.mate.carpool.ui.screen.home.vm.CarpoolListViewModel
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModel
import com.mate.carpool.ui.screen.register.RegisterDriverViewModel
import com.mate.carpool.ui.screen.splash.SplashViewModel
import com.mate.carpool.ui.theme.MateTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeBottomSheetLayout(
    refreshState: Boolean,
    userInfo: MemberModel,
    studentId: String,
    carpoolExistState: Boolean,
    carpoolList: List<TicketListModel>,
    ticketDetail: TicketModel,
    event: Event,
    snackBarMessage: SnackBarMessage,
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    onNavigateToReportView: (String) -> Unit,
    onNavigateToRegisterDriver: () -> Unit,
    isTicketIsMineOrNot: (List<TicketListModel>) -> Boolean,
    getMyPassengerId: (String) -> Long?,
    onRefresh: (String) -> Unit,
    setPassengerId: (Long) -> Unit,
    setStudentId: (String) -> Unit,
    setTicketId: (Long) -> Unit,
    setTicketIdFromMyTicket: ((Long) -> Unit) -> Unit,
    addNewPassengerToTicket: () -> Unit,
    updateTicketStatus: (TicketStatus) -> Unit,
    deletePassengerToTicket: () -> Unit,
    emitSnackBar: (SnackBarMessage) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scaffoldState = rememberScaffoldState()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){}

    if(snackBarMessage.contentMessage.isNotBlank())
        LaunchedEffect(key1 = snackBarMessage.contentMessage) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = snackBarMessage.contentMessage,
                duration =  SnackbarDuration.Indefinite,
                actionLabel = snackBarMessage.headerMessage
            )
        }

    if(event.type != BaseViewModel.EVENT_READY)
        LaunchedEffect(key1 = event.type){
            when (event.type) {
                HomeBottomSheetViewModel.EVENT_ADDED_PASSENGER_TO_TICKET-> {
                    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
                SplashViewModel.EVENT_GO_TO_HOME_SCREEN -> {
                    emitSnackBar(
                        SnackBarMessage(
                            headerMessage = "MATE에 성공적으로 로그인 했어요.",
                            contentMessage = when(userInfo.user.role){
                                MemberRole.Driver -> "티켓을 생성해 카풀을 운영해보세요."
                                MemberRole.Passenger -> "주변에 있는 카풀을 찾아보세요."
                            }
                        )
                    )
                }
                RegisterDriverViewModel.EVENT_REGISTERED_DRIVER_SUCCEED -> {
                    emitSnackBar(
                        SnackBarMessage(
                            headerMessage = "드라이버에 성공적으로 등록했어요.",
                            contentMessage = "티켓을 생성해 카풀을 운영해보세요."
                        )
                    )
                }
            }
        }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            Column() {
                SnackBarHostCustom(
                    headerMessage = it.currentSnackbarData?.message ?: "",
                    contentMessage = it.currentSnackbarData?.actionLabel ?: "",
                    snackBarHostState = scaffoldState.snackbarHostState,
                    disMissSnackBar = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() }
                )
                VerticalSpacer(height = 90.dp)
            }
        }
    ) {
        ModalBottomSheetLayout(
            sheetContent = {
                when(isTicketIsMineOrNot(userInfo.ticketList?: emptyList())){
                    true -> {
                        ReservationBottomSheetContent(
                            ticketDetail = ticketDetail,
                            userRole = userInfo.user.role,
                            userStudentId = userInfo.user.studentID,
                            userPassengerId = getMyPassengerId,
                            onCloseBottomSheet = { bottomSheetState.animateTo(ModalBottomSheetValue.Hidden) },
                            onBrowseOpenChatLink = { launcher.launch(Intent(Intent.ACTION_VIEW, Uri.parse(ticketDetail.openChatUrl))) },
                            onNavigateToReportView = { onNavigateToReportView(studentId) },
                            onRefresh = onRefresh,
                            setPassengerId = setPassengerId,
                            setStudentId = setStudentId,
                            deletePassengerFromTicket = deletePassengerToTicket,
                            updateTicketStatus = updateTicketStatus
                        )
                    }
                    false -> {
                        TicketBottomSheetContent(
                            ticketDetail = ticketDetail,
                            userProfile = userInfo.user.profile,
                            userRole = userInfo.user.role,
                            onCloseBottomSheet = { bottomSheetState.animateTo(ModalBottomSheetValue.Hidden) },
                            addNewPassengerToTicket = addNewPassengerToTicket,
                            onRefresh = onRefresh,
                            emitSnackBarMessage = emitSnackBar
                        )
                    }
                }
            },
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(it)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                ModalDrawer(
                    drawerContent = {
                        DrawerContent(
                            items = listOf(
                                DrawerItem.CONTACT,
                                DrawerItem.CLAUSE,
                                DrawerItem.LOGOUT,
                                DrawerItem.WITHDRAW
                            ),
                            onCloseDrawer = { scaffoldState.drawerState.close() }
                        )
                    },
                    drawerState = scaffoldState.drawerState
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
                        HomeView(
                            refreshState = refreshState,
                            carpoolExistState = carpoolExistState,
                            carpoolList = carpoolList,
                            userProfile = userInfo.user.profile,
                            userRole = userInfo.user.role,
                            setTicketId = setTicketId,
                            setTicketIdFromMyTicket = setTicketIdFromMyTicket,
                            onNavigateToCreateCarpool = onNavigateToCreateCarpool,
                            onNavigateToProfileView = onNavigateToProfileView,
                            onNavigateToRegisterDriver = onNavigateToRegisterDriver,
                            onRefresh = onRefresh,
                            onOpenBottomSheet = { bottomSheetState.animateTo(ModalBottomSheetValue.Expanded) },
                            onOpenDrawer = { scaffoldState.drawerState.open() }
                        )
                    }
                }
            }
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
    onRefresh: (String) -> Unit,
    onOpenBottomSheet: suspend () -> Unit,
    onOpenDrawer: suspend () -> Unit
) {
    Column() {
        HomeAppBar(
            profileImage = userProfile,
            goToProfileScreen = onNavigateToProfileView,
            onOpenDrawer = onOpenDrawer
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
            onNavigateToRegisterDriver = {},
            onOpenDrawer = {}
        )
    }
}
