package com.example.newsapp.presentation.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.data.remote.dto.Article
import com.example.newsapp.presentation.common.ArticleList
import com.example.newsapp.presentation.common.SearchBar
import com.example.newsapp.ui.theme.Dimens

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToDetail: (Article) -> Unit
) {
    val uiState by viewModel.stateUiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(top = Dimens.dimen24, start = Dimens.dimen24, end = Dimens.dimen24)
            .statusBarsPadding()
    ) {
        SearchBar(
            text = uiState.searchQuery,
            readOnly = false,
            onValueChange = { viewModel.updateSearchQuery(it) },
            onSearch = {
                Log.d("SearchScreen", "onSearchClicked ")
                viewModel.searchNews()
            }
        )
        Spacer(modifier = Modifier.height(Dimens.dimen24))
        uiState.articles?.collectAsLazyPagingItems()?.let {
            Log.d("SearchScreen", "articleList ${it} ")
            ArticleList(
                articles = it,
                onClick = navigateToDetail
            )
        }
    }
}