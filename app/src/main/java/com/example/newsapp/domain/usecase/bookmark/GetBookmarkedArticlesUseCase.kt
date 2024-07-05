package com.example.newsapp.domain.usecase.bookmark

import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.remote.dto.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkedArticlesUseCase @Inject constructor(
    private val newsDao: NewsDao
) {
    operator fun invoke() : Flow<List<Article>> {
        return newsDao.getArticles()
    }
}