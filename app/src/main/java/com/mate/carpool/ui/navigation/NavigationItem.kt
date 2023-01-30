package com.mate.carpool.ui.navigation

sealed class NavigationItem(
    val title:String,
    val route:String
){
    object Home: NavigationItem(
        title = "홈",
        route = "home"
    )
    object Announcement: NavigationItem(
        title = "공지",
        route = "announcement"
    )
    object Report: NavigationItem(
        title = "신고",
        route = "report/{studentId}"
    )
    object RegisterDriver: NavigationItem(
        title = "드라이버 등록",
        route = "registerDriver"
    )
}