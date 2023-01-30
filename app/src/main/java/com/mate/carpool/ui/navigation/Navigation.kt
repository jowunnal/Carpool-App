package com.mate.carpool.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
    reportViewModel: ReportViewModel = hiltViewModel()
){
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route
    ){

        composable(route = NavigationItem.Home.route) {
            val refreshState by carpoolListViewModel.refreshState.collectAsStateWithLifecycle()

            val userInfo by carpoolListViewModel.memberModelState.collectAsStateWithLifecycle()
            val carpoolExistState by carpoolListViewModel.carpoolExistState.collectAsStateWithLifecycle()
            val carpoolList by carpoolListViewModel.carpoolListState.collectAsStateWithLifecycle()

            val studentId by homeCarpoolBottomSheetViewModel.studentId.collectAsStateWithLifecycle()
            val ticketDetail by homeCarpoolBottomSheetViewModel.carpoolTicketState.collectAsStateWithLifecycle()

            val event by carpoolListViewModel.event.collectAsStateWithLifecycle(
                initialValue = Event(navArgs.event),
                lifecycleOwner = LocalLifecycleOwner.current
            )
            val snackBarMessage by homeCarpoolBottomSheetViewModel.snackbarMessage.collectAsStateWithLifecycle(
                initialValue = SnackBarMessage.getInitValues(),
                lifecycleOwner = LocalLifecycleOwner.current
            )

            HomeBottomSheetLayout(
                refreshState = refreshState,
                userInfo = userInfo,
                carpoolExistState = carpoolExistState,
                studentId = studentId,
                carpoolList = carpoolList,
                ticketDetail = ticketDetail,
                event = event,
                snackBarMessage = snackBarMessage,
                onNavigateToCreateCarpool = onNavigateToCreateCarpool,
                onNavigateToProfileView = onNavigateToProfileView,
                onNavigateToRegisterDriver = { navController.navigate("RegisterDriver") },
                onNavigateToReportView = fun(studentId:String){ navController.navigate("report/${studentId.toLong()}") },
                isTicketIsMineOrNot = homeCarpoolBottomSheetViewModel::isTicketIsMineOrNot,
                getMyPassengerId = homeCarpoolBottomSheetViewModel::getMyPassengerId,
                onRefresh = carpoolListViewModel::onRefresh,
                setPassengerId = homeCarpoolBottomSheetViewModel::setPassengerId,
                setStudentId = homeCarpoolBottomSheetViewModel::setStudentId,
                setTicketId = homeCarpoolBottomSheetViewModel::setTicketId,
                setTicketIdFromMyTicket = carpoolListViewModel::getMyTicket,
                addNewPassengerToTicket = homeCarpoolBottomSheetViewModel::addNewPassengerToTicket,
                updateTicketStatus = homeCarpoolBottomSheetViewModel::updateTicketStatus,
                deletePassengerToTicket = homeCarpoolBottomSheetViewModel::deletePassengerToTicket,
                emitSnackBar = homeCarpoolBottomSheetViewModel::emitSnackbar,
            )
        }

        composable(route = NavigationItem.RegisterDriver.route) {

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
                initialValue = Event("EVENT_READY"),
                lifecycleOwner = LocalLifecycleOwner.current
            )
            reportViewModel::init.invoke(it.arguments?.getLong("studentId")?:-1L)

            LaunchedEffect(key1 = event.type){
                when (event.type) {
                    ReportViewModel.EVENT_FINISH -> {
                        navController.popBackStack()
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
                onBackClick = rememberLambda { navController.popBackStack() }
            )
        }
    }
}