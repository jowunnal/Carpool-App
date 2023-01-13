package com.mate.carpool.ui.us.home.compose

sealed class NavigationModel(
    val title:String,
    val route:String
){
    object Home: NavigationModel("홈","home")
    object Announcement: NavigationModel("공지","announcement")
}