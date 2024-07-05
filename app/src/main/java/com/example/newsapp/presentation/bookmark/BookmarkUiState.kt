package com.example.newsapp.presentation.bookmark

import com.example.newsapp.data.remote.dto.Article

data class BookmarkUiState(
    val articles: List<Article> = emptyList<Article>()
)