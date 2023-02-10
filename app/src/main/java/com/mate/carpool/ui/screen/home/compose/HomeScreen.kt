package com.mate.carpool.ui.screen.home.compose

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.ui.base.Event
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.composable.SnackBarHostCustom
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.screen.createCarpool.vm.CreateTicketViewModel
import com.mate.carpool.ui.screen.home.compose.component.*
import com.mate.carpool.ui.screen.home.compose.component.appbar.HomeAppBar
import com.mate.carpool.ui.screen.home.compose.component.bottomsheet.ReservationBottomSheetContent
import com.mate.carpool.ui.screen.home.compose.component.bottomsheet.TicketBottomSheetContent
import com.mate.carpool.ui.screen.home.compose.component.drawer.DrawerContent
import com.mate.carpool.ui.screen.home.compose.component.drawer.DrawerItem
import com.mate.carpool.ui.screen.home.item.BottomSheetUiState
import com.mate.carpool.ui.screen.home.item.CarpoolListUiState
import com.mate.carpool.ui.screen.home.item.PassengerState
import com.mate.carpool.ui.screen.home.item.TicketListState
import com.mate.carpool.ui.screen.home.vm.CarpoolListViewModel
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModel
import com.mate.carpool.ui.screen.register.RegisterDriverViewModel
import com.mate.carpool.ui.screen.report.ReportViewModel
import com.mate.carpool.ui.screen.splash.SplashViewModel
import com.mate.carpool.ui.screen.ticketupdate.TicketUpdateViewModel
import com.mate.carpool.ui.theme.MateTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeBottomSheetLayout(
    carpoolListUiState: CarpoolListUiState,
    bottomSheetUiState: BottomSheetUiState,
    event: Event,
    snackBarMessage: SnackBarMessage,
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    onNavigateToReportView: (String, String) -> Unit,
    onNavigateToRegisterDriver: () -> Unit,
    onNavigateToTicketUpdate: () -> Unit,
    onNavigateToOnBoarding: () -> Unit,
    isTicketIsMineOrNot: (String, List<TicketListState>) -> Unit,
    setPassengerId: (String) -> Unit,
    setUserId: (String) -> Unit,
    onRefresh: (String) -> Unit,
    getTicketDetail: (String) -> Unit,
    getMyTicketDetail: () -> Unit,
    addNewPassengerToTicket: (String) -> Unit,
    deletePassengerFromTicket: (String, MemberRole) -> Unit,
    deleteMyTicket: (String) -> Unit,
    logout: () -> Unit,
    withDraw: () -> Unit,
    emitSnackBar: (SnackBarMessage) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    val scaffoldState = rememberScaffoldState()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    val coroutineScope = rememberCoroutineScope()

    if (snackBarMessage.headerMessage.isNotBlank()) LaunchedEffect(key1 = snackBarMessage.headerMessage) {
        scaffoldState.snackbarHostState.showSnackbar(
            message = snackBarMessage.headerMessage,
            duration = SnackbarDuration.Indefinite,
            actionLabel = snackBarMessage.contentMessage
        )
    }

    if (event.type != Event.EVENT_READY || event.type != Event.EVENT_FINISH || event != Event.getInitValues())
        OnActiveSnackBar(
            event = event,
            role = carpoolListUiState.userState.role,
            userTicketList = carpoolListUiState.ticketList,
            onOpenBottomSheet = { bottomSheetState.animateTo(ModalBottomSheetValue.Expanded) },
            emitSnackBar = emitSnackBar,
            onRefresh = onRefresh,
            isTicketIsMineOrNot = isTicketIsMineOrNot,
            getMyTicketDetail = getMyTicketDetail,
            onNavigateToOnBoarding = onNavigateToOnBoarding
        )

    BackHandler(enabled = bottomSheetState.isVisible || scaffoldState.drawerState.isOpen) {
        if (bottomSheetState.isVisible) coroutineScope.launch {
            bottomSheetState.hide()
        }
        else if (scaffoldState.drawerState.isOpen) coroutineScope.launch {
            scaffoldState.drawerState.close()
        }
    }

    Scaffold(scaffoldState = scaffoldState, snackbarHost = {
        Column() {
            SnackBarHostCustom(headerMessage = it.currentSnackbarData?.message ?: "",
                contentMessage = it.currentSnackbarData?.actionLabel ?: "",
                snackBarHostState = scaffoldState.snackbarHostState,
                disMissSnackBar = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() })
            VerticalSpacer(height = 90.dp)
        }
    }) {
        ModalBottomSheetLayout(
            sheetContent = {
                when (bottomSheetUiState.ticketIsMineOrNot) {
                    true -> {
                        ReservationBottomSheetContent(
                            ticketDetail = bottomSheetUiState.ticket,
                            userRole = carpoolListUiState.userState.role,
                            userId = carpoolListUiState.userState.id,
                            userPassengerId = (carpoolListUiState.userState as PassengerState).passengerId,
                            selectedMemberPassengerId = bottomSheetUiState.passengerId,
                            setPassengerId = setPassengerId,
                            setUserId = setUserId,
                            onCloseBottomSheet = { bottomSheetState.animateTo(ModalBottomSheetValue.Hidden) },
                            onBrowseOpenChatLink = {
                                launcher.launch(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(bottomSheetUiState.ticket.openChatUrl)
                                    )
                                )
                            },
                            onNavigateToReportView = onNavigateToReportView,
                            onNavigateToTicketUpdate = onNavigateToTicketUpdate,
                            onRefresh = onRefresh,
                            deletePassengerFromTicket = deletePassengerFromTicket,
                            deleteMyTicket = deleteMyTicket
                        )
                    }
                    false -> {
                        TicketBottomSheetContent(
                            ticketDetail = bottomSheetUiState.ticket,
                            userRole = carpoolListUiState.userState.role,
                            userTicket = bottomSheetUiState.userTicket,
                            onCloseBottomSheet = { bottomSheetState.animateTo(ModalBottomSheetValue.Hidden) },
                            addNewPassengerToTicket = addNewPassengerToTicket,
                            onRefresh = onRefresh
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
                            onCloseDrawer = { scaffoldState.drawerState.close() },
                            logout = logout,
                            withDraw = withDraw
                        )
                    },
                    drawerState = scaffoldState.drawerState
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        HomeView(refreshState = carpoolListUiState.refreshState,
                            carpoolExistState = carpoolListUiState.carpoolExistState,
                            carpoolList = carpoolListUiState.ticketList,
                            userProfile = carpoolListUiState.userState.profileImage,
                            userRole = carpoolListUiState.userState.role,
                            userTicketList = carpoolListUiState.ticketList,
                            isTicketIsMineOrNot = isTicketIsMineOrNot,
                            getMyTicketDetail = getMyTicketDetail,
                            getTicketDetail = getTicketDetail,
                            onNavigateToCreateCarpool = onNavigateToCreateCarpool,
                            onNavigateToProfileView = onNavigateToProfileView,
                            onNavigateToRegisterDriver = onNavigateToRegisterDriver,
                            onRefresh = onRefresh,
                            onOpenBottomSheet = { bottomSheetState.animateTo(ModalBottomSheetValue.Expanded) },
                            onOpenDrawer = { scaffoldState.drawerState.open() })
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeView(
    refreshState: Boolean,
    carpoolExistState: Boolean,
    carpoolList: List<TicketListState>,
    userProfile: String,
    userRole: MemberRole,
    userTicketList: List<TicketListState>,
    getMyTicketDetail: () -> Unit,
    getTicketDetail: (String) -> Unit,
    isTicketIsMineOrNot: (String, List<TicketListState>) -> Unit,
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
            Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp)
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
                    getTicketDetail = getTicketDetail,
                    onRefresh = onRefresh,
                    onOpenBottomSheet = onOpenBottomSheet,
                    isTicketIsMineOrNot = isTicketIsMineOrNot,
                    userTicketList = userTicketList
                )
            }

            MyCarpoolButton(
                carpoolExistState = carpoolExistState,
                getMyTicketDetail = getMyTicketDetail,
                onOpenBottomSheet = onOpenBottomSheet,
                isTicketIsMineOrNot = isTicketIsMineOrNot,
                userTicketList = userTicketList
            )
        }
    }
}

@Composable
private fun OnActiveSnackBar(
    event: Event,
    role: MemberRole,
    userTicketList: List<TicketListState>,
    onOpenBottomSheet: suspend () -> Unit,
    emitSnackBar: (SnackBarMessage) -> Unit,
    onRefresh: (String) -> Unit,
    getMyTicketDetail: () -> Unit,
    isTicketIsMineOrNot: (String, List<TicketListState>) -> Unit,
    onNavigateToOnBoarding: () -> Unit
) {
    LaunchedEffect(key1 = event.type) {
        when (event.type) {
            HomeBottomSheetViewModel.EVENT_ADDED_PASSENGER_TO_TICKET -> {
                getMyTicketDetail()
                isTicketIsMineOrNot(userTicketList[0].id, userTicketList)
                delay(50)
                onOpenBottomSheet()
            }
            SplashViewModel.EVENT_GO_TO_HOME_SCREEN -> {
                emitSnackBar(
                    SnackBarMessage(
                        headerMessage = "MATE에 성공적으로 로그인 했어요.", contentMessage = when (role) {
                            MemberRole.DRIVER -> "티켓을 생성해 카풀을 운영해보세요."
                            MemberRole.PASSENGER -> "주변에 있는 카풀을 찾아보세요."
                            MemberRole.ADMIN -> ""
                        }
                    )
                )
            }
            RegisterDriverViewModel.EVENT_REGISTERED_DRIVER_SUCCEED -> {
                emitSnackBar(
                    SnackBarMessage(
                        headerMessage = "드라이버에 성공적으로 등록했어요.", contentMessage = "티켓을 생성해 카풀을 운영해보세요."
                    )
                )
            }
            RegisterDriverViewModel.EVENT_REGISTERED_DRIVER_FAILED -> {
                emitSnackBar(
                    SnackBarMessage(
                        headerMessage = "예상치 못한 문제가 발생하였습니다.", contentMessage = "다시 시도해 주세요."
                    )
                )
            }
            ReportViewModel.EVENT_REPORTED_USER -> {
                emitSnackBar(
                    SnackBarMessage(
                        headerMessage = "신고접수가 완료됐어요.", contentMessage = "MATE팀이 확인 후 연락할게요."
                    )
                )
            }
            CreateTicketViewModel.EVENT_CREATED_TICKET -> {
                emitSnackBar(
                    SnackBarMessage(
                        headerMessage = "티켓이 성공적으로 생성되었어요.", contentMessage = ""
                    )
                )
                onRefresh(Event.EVENT_FINISH)
            }
            CreateTicketViewModel.EVENT_FAILED_CREATE_TICKET -> {
                emitSnackBar(
                    SnackBarMessage(
                        headerMessage = "티켓을 생성하는데 실패했어요.", contentMessage = "다시 생성해 주세요."
                    )
                )
            }
            CreateTicketViewModel.EVENT_FAILED_CREATE_TICKET_ALREADY_CREATED -> {
                emitSnackBar(
                    SnackBarMessage(
                        headerMessage = "이미 카풀을 모집했어요.",
                        contentMessage = "중복 방지를 위해 카풀 모집은 한 개만 가능해요."
                    )
                )
            }
            TicketUpdateViewModel.EVENT_UPDATED_TICKET -> {
                emitSnackBar(
                    SnackBarMessage(
                        headerMessage = "티켓이 성공적으로 수정되었어요."
                    )
                )
            }
            CarpoolListViewModel.LOGOUT_SUCCESS -> {
                onNavigateToOnBoarding()
            }
            CarpoolListViewModel.LOGOUT_FAILED -> {
                emitSnackBar(
                    SnackBarMessage(
                        headerMessage = "로그아웃을 실패했어요.",
                        contentMessage = "다시 시도해 주세요."
                    )
                )
            }
            CarpoolListViewModel.WITHDRAW_SUCCESS -> {
                onNavigateToOnBoarding()
            }
            CarpoolListViewModel.WITHDRAW_FAILED -> {
                emitSnackBar(
                    SnackBarMessage(
                        headerMessage = "회원탈퇴를 실패했어요.",
                        contentMessage = "다시 시도해 주세요."
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    MateTheme {
        HomeView(refreshState = false,
            carpoolExistState = true,
            carpoolList = listOf(
                TicketListState(
                    id = "1",
                    profileImage = "",
                    startArea = "해운대앞바다",
                    startTime = 25200L,
                    recruitPerson = 3,
                    currentPersonCount = 1,
                    dayStatus = DayStatus.AM,
                    available = true
                ), TicketListState(
                    id = "2",
                    profileImage = "",
                    startArea = "부산대학교앞",
                    startTime = 25200L,
                    recruitPerson = 3,
                    currentPersonCount = 1,
                    dayStatus = DayStatus.AM,
                    available = true
                ), TicketListState(
                    id = "3",
                    profileImage = "",
                    startArea = "하단역",
                    startTime = 25200L,
                    recruitPerson = 3,
                    currentPersonCount = 1,
                    dayStatus = DayStatus.AM,
                    available = false
                ), TicketListState(
                    id = "4",
                    profileImage = "",
                    startArea = "부경대/경성대",
                    startTime = 25200L,
                    recruitPerson = 3,
                    currentPersonCount = 1,
                    dayStatus = DayStatus.AM,
                    available = false
                )
            ),
            userProfile = "",
            userRole = MemberRole.DRIVER,
            getTicketDetail = {},
            getMyTicketDetail = {},
            onNavigateToCreateCarpool = {},
            onNavigateToProfileView = {},
            onRefresh = {},
            onOpenBottomSheet = {},
            onNavigateToRegisterDriver = {},
            onOpenDrawer = {},
            userTicketList = emptyList(),
            isTicketIsMineOrNot = { _, _ -> })
    }
}
