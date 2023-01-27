package com.mate.carpool.ui.navigation

import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mate.carpool.ui.screen.home.compose.HomeBottomSheetLayout
import com.mate.carpool.ui.screen.home.vm.CarpoolListViewModel
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModel

@Composable
fun NavigationGraph(
    navController:NavHostController,
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    carpoolListViewModel: CarpoolListViewModel = hiltViewModel(),
    homeCarpoolBottomSheetViewModel: HomeBottomSheetViewModel = hiltViewModel()
){
    NavHost(navController = navController, startDestination = NavigationModel.Home.route){

        composable(route = NavigationModel.Home.route){
            HomeBottomSheetLayout(
                onNavigateToCreateCarpool = onNavigateToCreateCarpool,
                onNavigateToProfileView = onNavigateToProfileView,
                fragmentManager = ((LocalContext.current as ContextWrapper).baseContext as FragmentActivity).supportFragmentManager,
                homeCarpoolBottomSheetViewModel = homeCarpoolBottomSheetViewModel,
                carpoolListViewModel = carpoolListViewModel
            )
        }

        composable(route = NavigationModel.Announcement.route){
        }

    }
}