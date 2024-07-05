package com.example.newsapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.data.remote.dto.Article
import com.example.newsapp.navigation.screen.Screen
import com.example.newsapp.presentation.common.ArticleList
import com.example.newsapp.presentation.common.SearchBar
import com.example.newsapp.ui.theme.Dimens

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigate: (String) -> Unit,
    navigateToDetail: (Article) -> Unit
) {
    val homeUiState by viewModel.homeUiState.collectAsState()

    val lazyPagingItems: LazyPagingItems<Article>? =
        homeUiState.articles?.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Dimens.dimen24, start = Dimens.dimen24, end = Dimens.dimen24)
            .statusBarsPadding() // Adds padding to accommodate status bar inset
    ) {
        Image(painter = painterResource(id = R.drawable.ic_logo), contentDescription = "App logo")
        Spacer(modifier = Modifier.height(Dimens.dimen24))
        SearchBar(
            modifier = Modifier
                .fillMaxWidth(),
            text = "",
            readOnly = true,
            onValueChange = {},
            onSearch = {},
            onClick = {
                navigate(Screen.SearchScreen.route)
            }
        )

        Spacer(modifier = Modifier.height(Dimens.dimen24))

        lazyPagingItems?.let {
            ArticleList(onClick = navigateToDetail, articles = lazyPagingItems)
        }

    }


}