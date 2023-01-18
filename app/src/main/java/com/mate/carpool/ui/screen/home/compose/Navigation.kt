package com.mate.carpool.ui.us.home.compose

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mate.carpool.ui.screen.home.compose.HomeCarpoolSheet
import com.mate.carpool.ui.screen.home.vm.CarpoolListViewModel
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModel

@Composable
fun NavigationGraph(
    navController:NavHostController,
    onNavigateToCreateCarpool: () -> Unit,
    homeCarpoolListViewModel: CarpoolListViewModel = hiltViewModel(),
    homeCarpoolBottomSheetViewModel: HomeBottomSheetViewModel = hiltViewModel()
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