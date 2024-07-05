package com.example.newsapp.domain.usecase.search

import android.util.Log
import androidx.paging.PagingData
import com.example.newsapp.data.remote.dto.Article
import com.example.newsapp.domain.repository.news.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        Log.d("SearchScreen", "in usecase searchNewsUseCase() query ${searchQuery}")
        return newsRepository.searchNews(
            searchQuery = searchQuery,
            sources = sources
        )
    }
}