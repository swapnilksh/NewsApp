package com.example.newsapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newsapp.data.remote.dto.Article
import com.example.newsapp.presentation.home.components.ArticleCard
import com.example.newsapp.ui.theme.Dimens

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    if (handlePagingResult(articles = articles))
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.dimen24), //Place children such that each two adjacent ones are spaced by a fixed space distance across the main axis.
            contentPadding = PaddingValues(all = Dimens.dimen6)
        ) {
            items(
                count = articles.itemCount
            ) { index ->
                articles[index]?.let { article ->
                    ArticleCard(article = article) {
                        onClick.invoke(article)
                    }
                }
            }
        }
}

@Composable
fun handlePagingResult(articles: LazyPagingItems<Article>): Boolean {
    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen(error = error)
            false
        }

        else -> {
            true
        }
    }
}

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.dimen24)) {
        repeat(10) {
            ArticleShimmerEffect(
                modifier = Modifier.padding(horizontal = Dimens.dimen24)
            )
        }
    }
}

@Composable
fun ArticlesListWithoutPagination(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
) {
    if (articles.isEmpty()) {
        EmptyScreen()
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.dimen24),
        contentPadding = PaddingValues(all = Dimens.dimen6)
    ) {
        items(
            count = articles.size,
        ) {
            articles[it]?.let { article ->
                ArticleCard(article = article, onClick = { onClick(article) })
            }
        }
    }
}

