package com.example.newsapp.presentation.search


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.remote.dto.Article
import com.example.newsapp.domain.usecase.search.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {

    private var _searchUiState = MutableStateFlow(SearchState())
    val stateUiState = _searchUiState.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchUiState.update { state ->
            state.copy(
                searchQuery = query
            )
        }
    }

    fun searchNews() {
        Log.d("SearchScreen", "in viewmodel searchNews() ")
        searchNewsUseCase(
            searchQuery = _searchUiState.value.searchQuery,
            sources = listOf("bbc-news", "abc-news", "al-jazeera-english")
        ).cachedIn(viewModelScope).onEach { data ->
            val pagingDataFlow: Flow<PagingData<Article>> = flow {
                data?.let { pagingData ->
                    emit(pagingData)
                }
            }
            Log.d("SearchScreen", "in viewmodel searchNews() onEach data $pagingDataFlow")
            _searchUiState.update { state ->
                state.copy(articles = pagingDataFlow)
            }
        }.launchIn(viewModelScope)
    }
}