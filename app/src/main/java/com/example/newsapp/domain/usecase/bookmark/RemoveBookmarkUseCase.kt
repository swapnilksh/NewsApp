package com.example.newsapp.domain.usecase.bookmark

import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.remote.dto.Article
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(article: Article) {
        newsDao.delete(article)
    }
}