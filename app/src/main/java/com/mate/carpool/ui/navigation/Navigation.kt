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
import com.mate.carpool.ui.screen.home.vm.CarpoolListViewModelInterface
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModel
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModelInterface

@Composable
fun NavigationGraph(
    navController:NavHostController,
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    homeCarpoolListViewModel: CarpoolListViewModelInterface = hiltViewModel<CarpoolListViewModel>(),
    homeCarpoolBottomSheetViewModel: HomeBottomSheetViewModelInterface = hiltViewModel<HomeBottomSheetViewModel>()
){
    NavHost(navController = navController, startDestination = NavigationModel.Home.route){
        composable(route = NavigationModel.Home.route){
            HomeBottomSheetLayout(
                onNavigateToCreateCarpool = onNavigateToCreateCarpool,
                onNavigateToProfileView = onNavigateToProfileView,
                fragmentManager = ((LocalContext.current as ContextWrapper).baseContext as FragmentActivity).supportFragmentManager,
                homeCarpoolBottomSheetViewModel = homeCarpoolBottomSheetViewModel,
                carpoolListViewModel = homeCarpoolListViewModel
            )
        }
        composable(route = NavigationModel.Announcement.route){
        }
    }
}