package com.example.newsapp.navigation.screen

sealed class Screen(val route: String) {
    object OnBoardingScreen: Screen("on_boarding")
    object AppStartNavigation : Screen(route = "appStartNavigation")
    object HomeScreen: Screen("home")

    object SearchScreen : Screen(route = "searchScreen")

    object BookmarkScreen : Screen(route = "bookMarkScreen")

    object DetailsScreen : Screen(route = "detailsScreen")

    object NewsNavigation : Screen(route = "newsNavigation")

    object NewsNavigatorScreen : Screen(route = "newsNavigator")
}