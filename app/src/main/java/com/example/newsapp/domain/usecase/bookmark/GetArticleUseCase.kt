package com.example.newsapp.domain.usecase.bookmark

import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.remote.dto.Article
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(url: String): Article? {
        return newsDao.getArticle(url)
    }
}