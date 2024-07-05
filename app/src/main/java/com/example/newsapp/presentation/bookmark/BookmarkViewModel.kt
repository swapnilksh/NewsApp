package com.example.newsapp.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecase.bookmark.GetBookmarkedArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkedArticlesUseCase: GetBookmarkedArticlesUseCase
) : ViewModel() {

    /*
    * Here unlike HomeViewModel and SearchViewModel I have intentionally used mutableStateOf
    * instead of MutableStateOf() just for educational purpose. This State class is compose State class which
    * is observable to update ui.
    * Where as StateFlow is observable just like LiveData. As per android architecture guide it is
    * recommended to use StateFlow but as per required usecase.
    * */
    private val _uiState = mutableStateOf(BookmarkUiState())
    val uiState: State<BookmarkUiState> = _uiState

    init {
        getBookmarkedArticles()
    }

    private fun getBookmarkedArticles() {
        bookmarkedArticlesUseCase().onEach {
            _uiState.value = _uiState.value.copy(articles = it)
        }.launchIn(viewModelScope)
    }
}
