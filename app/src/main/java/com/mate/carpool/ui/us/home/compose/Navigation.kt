package com.mate.carpool.ui.us.home.compose

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mate.carpool.ui.compose.HomeCarpoolSheet
import com.mate.carpool.ui.us.home.vm.HomeCarpoolBottomSheetViewModel
import com.mate.carpool.ui.us.home.vm.HomeCarpoolListViewModel

@Composable
fun NavigationGraph(
    navController:NavHostController,
    onNavigateToCreateCarpool: () -> Unit,
    homeCarpoolListViewModel: HomeCarpoolListViewModel = hiltViewModel(),
    homeCarpoolBottomSheetViewModel: HomeCarpoolBottomSheetViewModel = hiltViewModel()
){
    NavHost(navController = navController, startDestination = NavigationModel.Home.route){
        composable(route = NavigationModel.Home.route){
            HomeCarpoolSheet(
                onNavigateToCreateCarpool = onNavigateToCreateCarpool,
            )
        }
        composable(route = NavigationModel.Announcement.route){
            AnnouncementView(

            )
        }
    }
}