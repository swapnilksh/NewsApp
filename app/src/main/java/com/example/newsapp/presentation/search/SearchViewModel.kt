package com.example.newsapp.presentation.search


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.remote.dto.Article
import com.example.newsapp.domain.usecase.search.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {

    private var _searchUiState = MutableStateFlow(SearchState())
    val stateUiState = _searchUiState.asStateFlow()

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class)
     fun observeSearchQuery() {
        viewModelScope.launch {
            _searchUiState
                .map { it.searchQuery }
                .debounce(500) // Add debounce to avoid frequent API calls
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.length > 3)
                        searchNews(query)
                    else
                        _searchUiState.update { state ->
                            state.copy(
                                articles = null,
                            )
                        }
                }
        }
    }


    fun updateSearchQuery(query: String) {
        _searchUiState.update { state ->
            state.copy(
                searchQuery = query
            )
        }
    }

    fun searchNews(query: String? = null) {
        searchNewsUseCase(
            searchQuery = query ?: _searchUiState.value.searchQuery,
            sources = listOf("bbc-news", "abc-news", "al-jazeera-english")
        ).cachedIn(viewModelScope).onEach { data ->
            val pagingDataFlow: Flow<PagingData<Article>> = flow {
                data?.let { pagingData ->
                    emit(pagingData)
                }
            }
            _searchUiState.update { state ->
                state.copy(articles = pagingDataFlow)
            }
        }.launchIn(viewModelScope)
    }

    fun resetSearchState() {
        _searchUiState.update { state ->
            state.copy(
                searchQuery = "",
                articles = null,
            )

        }
    }

  /*  fun clearSearchState() {
        SearchState()
    }*/
}