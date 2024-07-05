package com.example.newsapp.domain.repository.news

import androidx.paging.PagingData
import com.example.newsapp.data.remote.dto.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>
    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>
}