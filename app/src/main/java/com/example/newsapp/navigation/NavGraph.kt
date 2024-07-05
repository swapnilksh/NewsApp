package com.example.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.navigation.news_navigator.NewsNavigator
import com.example.newsapp.presentation.onboarding.OnBoardingScreen
import com.example.newsapp.presentation.onboarding.OnBoardingViewModel
import com.example.newsapp.navigation.screen.Screen

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Screen.AppStartNavigation.route,
            startDestination = Screen.OnBoardingScreen.route
        ) {
            composable(route = Screen.OnBoardingScreen.route) {
             //   val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen()
            }
        }

        navigation(
            route = Screen.NewsNavigation.route,
            startDestination = Screen.NewsNavigatorScreen.route
        ) {
            composable(route = Screen.NewsNavigatorScreen.route){
                NewsNavigator()
            }
        }
    }
}