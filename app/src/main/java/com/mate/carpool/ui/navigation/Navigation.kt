package com.mate.carpool.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import com.mate.carpool.ui.composable.rememberLambda
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
    navController:NavHostController,
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    carpoolListViewModel: CarpoolListViewModel = hiltViewModel(),
    homeCarpoolBottomSheetViewModel: HomeBottomSheetViewModel = hiltViewModel(),
    reportViewModel: ReportViewModel = hiltViewModel(),
    registerDriverViewModel: RegisterDriverViewModel = hiltViewModel()
){
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route
    ){

        composable(
            route = NavigationItem.Home.route,
            arguments = listOf(navArgument("event") { type = NavType.StringType })
        ) {
            val refreshState by carpoolListViewModel.refreshState.collectAsStateWithLifecycle()

            val userInfo by carpoolListViewModel.memberModelState.collectAsStateWithLifecycle()
            val carpoolExistState by carpoolListViewModel.carpoolExistState.collectAsStateWithLifecycle()
            val carpoolList by carpoolListViewModel.carpoolListState.collectAsStateWithLifecycle()

            val bottomSheetUiState by homeCarpoolBottomSheetViewModel.uiState.collectAsStateWithLifecycle()

            val event by carpoolListViewModel.event.collectAsStateWithLifecycle(
                initialValue = Event(it.arguments?.getString("event",navArgs.event)?:navArgs.event),
                lifecycle = LocalLifecycleOwner.current.lifecycle
            )

            val snackBarMessage by homeCarpoolBottomSheetViewModel.snackbarMessage.collectAsState(
                initial = SnackBarMessage.getInitValues()
            )

            HomeBottomSheetLayout(
                refreshState = refreshState,
                userInfo = userInfo,
                carpoolExistState = carpoolExistState,
                bottomSheetUiState = bottomSheetUiState,
                carpoolList = carpoolList,
                event = event,
                snackBarMessage = snackBarMessage,
                onNavigateToCreateCarpool = onNavigateToCreateCarpool,
                onNavigateToProfileView = onNavigateToProfileView,
                onNavigateToRegisterDriver = { navController.navigate(NavigationItem.RegisterDriver.StepCarImage.route) },
                onNavigateToReportView = fun(studentId:String){ navController.navigate("report/${studentId.toLong()}") },
                isTicketIsMineOrNot = homeCarpoolBottomSheetViewModel::isTicketIsMineOrNot,
                getMyPassengerId = homeCarpoolBottomSheetViewModel::getMyPassengerId,
                getTicketDetail = homeCarpoolBottomSheetViewModel::getTicketDetail,
                onRefresh = carpoolListViewModel::onRefresh,
                setPassengerId = homeCarpoolBottomSheetViewModel::setPassengerId,
                setStudentId = homeCarpoolBottomSheetViewModel::setStudentId,
                getMyTicketDetail = homeCarpoolBottomSheetViewModel::getMyTicketDetail,
                addNewPassengerToTicket = homeCarpoolBottomSheetViewModel::addNewPassengerToTicket,
                updateTicketStatus = homeCarpoolBottomSheetViewModel::updateTicketStatus,
                deletePassengerToTicket = homeCarpoolBottomSheetViewModel::deletePassengerToTicket,
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
                            inclusive=true
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
                onNavigateToNextStep = {navController.navigate(NavigationItem.RegisterDriver.StepPhoneNumber.route)}
            )
        }
        composable(route = NavigationItem.RegisterDriver.StepPhoneNumber.route) {
            val uiState by registerDriverViewModel.uiState.collectAsStateWithLifecycle()

            val event by registerDriverViewModel.event.collectAsStateWithLifecycle(
                initialValue = Event.getInitValues(),
                lifecycle = LocalLifecycleOwner.current.lifecycle
            )

            RegisterDriverStepPhoneNumberScreen(
                uiState = uiState,
                onPhoneNumberEdit = registerDriverViewModel::setPhoneNumber,
                onCarNumberEdit = registerDriverViewModel::setCarNumber,
                onNavigatePopBackStack = { navController.popBackStack() },
                onNavigateToNextStep = {
                    navController.navigate("home/${RegisterDriverViewModel.EVENT_REGISTERED_DRIVER_SUCCEED}") {
                        popUpTo(NavigationItem.Home.route) { inclusive = true }
                    }
                },
                onFetch = registerDriverViewModel::fetch
            )
        }

        composable(route = NavigationItem.Announcement.route){
        }

        composable(
            route = NavigationItem.Report.route,
            arguments = listOf(navArgument("studentId") { type = NavType.LongType })
        ) {
            val reason by reportViewModel.reason.collectAsStateWithLifecycle()
            val description by reportViewModel.description.collectAsStateWithLifecycle()
            val enableReport by reportViewModel.enableReport.collectAsStateWithLifecycle()

            val event by reportViewModel.event.collectAsStateWithLifecycle(
                initialValue = Event.getInitValues(),
                lifecycleOwner = LocalLifecycleOwner.current
            )
            reportViewModel::init.invoke(it.arguments?.getLong("studentId")?:-1L)

            if(event != Event.getInitValues())
                LaunchedEffect(key1 = event.type){
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
    }
}