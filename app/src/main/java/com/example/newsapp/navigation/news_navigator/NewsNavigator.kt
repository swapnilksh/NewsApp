package com.example.newsapp.navigation.news_navigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.R
import com.example.newsapp.data.remote.dto.Article
import com.example.newsapp.navigation.news_navigator.components.BottomNavigation
import com.example.newsapp.navigation.news_navigator.model.BottomNavigationItem
import com.example.newsapp.navigation.screen.Screen
import com.example.newsapp.presentation.bookmark.BookmarkScreen
import com.example.newsapp.presentation.bookmark.BookmarkViewModel
import com.example.newsapp.presentation.details.DetailsScreen
import com.example.newsapp.presentation.home.HomeScreen
import com.example.newsapp.presentation.home.HomeViewModel
import com.example.newsapp.presentation.search.SearchScreen
import com.example.newsapp.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Screen.HomeScreen.route -> 0
        Screen.SearchScreen.route -> 1
        Screen.BookmarkScreen.route -> 2
        else -> 0
    }


    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Screen.HomeScreen.route ||
                backStackState?.destination?.route == Screen.SearchScreen.route ||
                backStackState?.destination?.route == Screen.BookmarkScreen.route
    }


    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible)
            BottomNavigation(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController,
                            route = Screen.HomeScreen.route
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Screen.SearchScreen.route
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Screen.BookmarkScreen.route
                        )
                    }
                }
            )
    }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Screen.HomeScreen.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    viewModel = homeViewModel,
                    navigate = { navigateToTab(navController = navController, route = it) },
                    navigateToDetail = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    })
            }
            composable(route = Screen.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                OnBackClickStateSaver(navController = navController)
                SearchScreen(viewModel = viewModel) { article ->
                    navigateToDetails(navController, article)
                }
            }
            composable(route = Screen.DetailsScreen.route) {
                //previousBackStackEntry?.savedStateHandle?.get gives us data from backstack
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailsScreen(
                            article = article,
                            onBackClick = { navController.navigateUp() }
                        )
                    }

            }
            composable(route = Screen.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.uiState.value
                OnBackClickStateSaver(navController = navController)
                BookmarkScreen(uiState = state) { article ->
                    navigateToDetails(
                        navController = navController,
                        article = article
                    )
                }
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Screen.HomeScreen.route
        )
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    // With the help of currentBackStackEntry.savedStatehandle we can fetch data in destination screen.
    // savedStateHandle stores data in bundle.
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Screen.DetailsScreen.route
    )
}