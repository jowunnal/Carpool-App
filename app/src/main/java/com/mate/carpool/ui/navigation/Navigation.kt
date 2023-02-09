package com.mate.carpool.ui.navigation

import android.content.ContextWrapper
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mate.carpool.ui.base.Event
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.screen.ticketupdate.TicketUpdateScreen
import com.mate.carpool.ui.screen.ticketupdate.TicketUpdateViewModel
import com.mate.carpool.ui.screen.home.compose.HomeBottomSheetLayout
import com.mate.carpool.ui.screen.home.vm.CarpoolListViewModel
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModel
import com.mate.carpool.ui.screen.register.RegisterDriverStepCarImageScreen
import com.mate.carpool.ui.screen.register.RegisterDriverStepCarNumberScreen
import com.mate.carpool.ui.screen.register.RegisterDriverStepPhoneNumberScreen
import com.mate.carpool.ui.screen.register.RegisterDriverViewModel
import com.mate.carpool.ui.screen.report.ReportScreen
import com.mate.carpool.ui.screen.report.ReportViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun NavigationGraph(
    navArgs: NavigationFragmentArgs,
    navController: NavHostController,
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    carpoolListViewModel: CarpoolListViewModel = hiltViewModel(),
    homeCarpoolBottomSheetViewModel: HomeBottomSheetViewModel = hiltViewModel(),
    reportViewModel: ReportViewModel = hiltViewModel(),
    registerDriverViewModel: RegisterDriverViewModel = hiltViewModel(),
    ticketUpdateViewModel: TicketUpdateViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route
    ) {

        composable(
            route = NavigationItem.Home.route,
            arguments = listOf(navArgument("event") { type = NavType.StringType })
        ) {
            val carpoolListUiState by carpoolListViewModel.uiState.collectAsStateWithLifecycle()
            val bottomSheetUiState by homeCarpoolBottomSheetViewModel.uiState.collectAsStateWithLifecycle()

            val event by carpoolListViewModel.event.collectAsStateWithLifecycle(
                initialValue = Event(
                    it.arguments?.getString("event", navArgs.event) ?: navArgs.event
                ),
                lifecycle = LocalLifecycleOwner.current.lifecycle
            )

            val snackBarMessage by homeCarpoolBottomSheetViewModel.snackbarMessage.collectAsState(
                initial = SnackBarMessage.getInitValues()
            )

            HomeBottomSheetLayout(
                carpoolListUiState = carpoolListUiState,
                bottomSheetUiState = bottomSheetUiState,
                event = event,
                snackBarMessage = snackBarMessage,
                onNavigateToCreateCarpool = onNavigateToCreateCarpool,
                onNavigateToProfileView = onNavigateToProfileView,
                onNavigateToRegisterDriver = { navController.navigate(NavigationItem.RegisterDriver.StepCarImage.route) },
                onNavigateToReportView = { ticketId: String, userId: String ->
                    navController.navigate(
                        "report/${ticketId}/${userId}"
                    )
                },
                onNavigateToTicketUpdate = { navController.navigate(NavigationItem.TicketUpdate.route) },
                isTicketIsMineOrNot = homeCarpoolBottomSheetViewModel::isTicketIsMineOrNot,
                setPassengerId = homeCarpoolBottomSheetViewModel::setPassengerId,
                setUserId = homeCarpoolBottomSheetViewModel::setUserId,
                onRefresh = carpoolListViewModel::onRefresh,
                getTicketDetail = homeCarpoolBottomSheetViewModel::getTicketDetail,
                getMyTicketDetail = homeCarpoolBottomSheetViewModel::getMyTicketDetail,
                addNewPassengerToTicket = homeCarpoolBottomSheetViewModel::addNewPassengerToTicket,
                deletePassengerFromTicket = homeCarpoolBottomSheetViewModel::deletePassengerFromTicket,
                deleteMyTicket = homeCarpoolBottomSheetViewModel::deleteMyTicket,
                emitSnackBar = homeCarpoolBottomSheetViewModel::emitSnackbar
            )
        }

        composable(route = NavigationItem.RegisterDriver.StepCarImage.route) {
            val uiState by registerDriverViewModel.uiState.collectAsStateWithLifecycle()

            RegisterDriverStepCarImageScreen(
                uiState = uiState,
                setCarImage = registerDriverViewModel::setCarImage,
                onNavigatePopBackStack = {
                    navController.navigate(route = "home/${Event.getInitValues()}") {
                        popUpTo(NavigationItem.Home.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToNextStep = { navController.navigate(NavigationItem.RegisterDriver.StepCarNumber.route) }
            )
        }
        composable(route = NavigationItem.RegisterDriver.StepCarNumber.route) {
            val uiState by registerDriverViewModel.uiState.collectAsStateWithLifecycle()

            RegisterDriverStepCarNumberScreen(
                uiState = uiState,
                onCarNumberEdit = registerDriverViewModel::setCarNumber,
                onNavigatePopBackStack = { navController.popBackStack() },
                onNavigateToNextStep = { navController.navigate(NavigationItem.RegisterDriver.StepPhoneNumber.route) }
            )
        }
        composable(route = NavigationItem.RegisterDriver.StepPhoneNumber.route) {
            val uiState by registerDriverViewModel.uiState.collectAsStateWithLifecycle()

            val event by registerDriverViewModel.event.collectAsStateWithLifecycle(
                initialValue = Event.getInitValues(),
                lifecycle = LocalLifecycleOwner.current.lifecycle
            )

            if (event != Event.getInitValues())
                LaunchedEffect(key1 = event.type) {
                    navController.navigate("home/${event.type}") {
                        popUpTo(NavigationItem.Home.route) { inclusive = true }
                    }
                }

            RegisterDriverStepPhoneNumberScreen(
                uiState = uiState,
                onPhoneNumberEdit = registerDriverViewModel::setPhoneNumber,
                onCarNumberEdit = registerDriverViewModel::setCarNumber,
                onNavigatePopBackStack = { navController.popBackStack() },
                onFetch = registerDriverViewModel::fetch
            )
        }

        composable(route = NavigationItem.Announcement.route) {
        }

        composable(
            route = NavigationItem.Report.route,
            arguments = listOf(
                navArgument("ticketId") { type = NavType.StringType },
                navArgument("userId") { type = NavType.StringType }
            )
        ) {
            val reason by reportViewModel.reason.collectAsStateWithLifecycle()
            val description by reportViewModel.description.collectAsStateWithLifecycle()
            val enableReport by reportViewModel.enableReport.collectAsStateWithLifecycle()

            val event by reportViewModel.event.collectAsStateWithLifecycle(
                initialValue = Event.getInitValues(),
                lifecycleOwner = LocalLifecycleOwner.current
            )

            reportViewModel::init.invoke(
                it.arguments?.getString("ticketId") ?: "-1",
                it.arguments?.getString("userId") ?: "-1"
            )

            if (event != Event.getInitValues())
                LaunchedEffect(key1 = event.type) {
                    navController.navigate("home/${event.type}") {
                        popUpTo(NavigationItem.Home.route) {
                            inclusive = true
                        }
                    }
                }

            ReportScreen(
                selectedReason = reason,
                description = description,
                enableReport = enableReport,
                onDescriptionEdit = reportViewModel::setDescription,
                onSelectReason = reportViewModel::selectReason,
                onDeselectReason = reportViewModel::deselectReason,
                onReportClick = reportViewModel::report,
                onBackClick = {
                    navController.navigate("home/${Event.getInitValues()}") {
                        popUpTo(NavigationItem.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = NavigationItem.TicketUpdate.route) {
            val ticketDetail by ticketUpdateViewModel.uiState.collectAsStateWithLifecycle()
            val context = LocalContext.current
            val fragmentManager =
                ((context as ContextWrapper).baseContext as FragmentActivity).supportFragmentManager

            TicketUpdateScreen(
                ticketDetail = ticketDetail,
                fragmentManager = fragmentManager,
                setStartArea = ticketUpdateViewModel::setStartArea,
                setBoardingPlace = ticketUpdateViewModel::setBoardingPlace,
                setStartTime = ticketUpdateViewModel::setStartTime,
                setOpenChatLink = ticketUpdateViewModel::setOpenChatLink,
                setRecruitPersonCount = ticketUpdateViewModel::setRecruitPersonCount,
                setFee = ticketUpdateViewModel::setFee,
                onNavigateToHome = {
                    navController.navigate("home/${Event.getInitValues()}") {
                        popUpTo(NavigationItem.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}