package com.example.newsapp.presentation.home

import androidx.paging.PagingData
import com.example.newsapp.data.remote.dto.Article
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val isLoading: Boolean = false,
    val articles: Flow<PagingData<Article>>? = null,
    val error: String = ""
)