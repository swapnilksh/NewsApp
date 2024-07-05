package com.example.newsapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.remote.dto.Article
import com.example.newsapp.domain.usecase.bookmark.GetArticleUseCase
import com.example.newsapp.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.example.newsapp.domain.usecase.bookmark.SaveArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val saveArticleUseCase: SaveArticleUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val getArticleUseCase: GetArticleUseCase
) : ViewModel() {

    private val _bookmarkStatus = MutableStateFlow<String?>(null)
    val bookmarkStatus: StateFlow<String?> = _bookmarkStatus

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked

    fun updateBookmark(article: Article) {
        viewModelScope.launch {
            val articleData = getArticleUseCase(article.url)
            if (articleData == null) {
                try {
                    saveArticleUseCase(article)
                    _isBookmarked.value = true
                    updateBookmarkStatus("Article Saved")
                } catch (e: Exception) {
                    updateBookmarkStatus("Unable to save the Article ")
                }
            } else {
                try {
                    removeBookmarkUseCase(article)
                    _isBookmarked.value = false
                    updateBookmarkStatus("Article Removed")
                } catch (e: Exception) {
                    updateBookmarkStatus("Unable to remove the Article")
                }

            }
        }
    }

    private fun updateBookmarkStatus(status: String) {
        _bookmarkStatus.value = status
    }

    fun resetStatus() {
        _bookmarkStatus.value = null
    }
}