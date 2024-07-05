package com.example.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.common.Result
import com.example.newsapp.data.remote.dto.Article
import com.example.newsapp.domain.usecase.news.GetNewsUseCase
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
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        getArticles()
    }

    /* private fun getArticles() {
         getNewsUseCase(
             listOf(
                 "bbc-news",
                 "abc-news",
                 "al-jazeera-english"
             )
         ).onEach { result ->
             when (result) {
                 is Result.Success -> {
                     val pagingDataFlow: Flow<PagingData<Article>> = flow {
                         result.data?.let { pagingData ->
                             emit(pagingData)
                         }
                     }
                     _homeUiState.update { state ->
                         state.copy(
                             isLoading = false,
                             articles = pagingDataFlow
                         )
                     }
                 }

                 is Result.Error -> {
                     _homeUiState.update { state ->
                         state.copy(
                             isLoading = false,
                             error = result.message ?: "Unexpeted Error occurred"
                         )
                     }
                 }

                 is Result.Loading -> {
                     _homeUiState.update { state ->
                         state.copy(
                             isLoading = true,
                         )
                     }
                 }
             }

         }.launchIn(viewModelScope)

     }*/

    private fun getArticles() {
        getNewsUseCase(
            listOf(
                "bbc-news",
                "abc-news",
                "al-jazeera-english"
            )
        ).onEach { result ->
                when (result) {
                    is Result.Success -> {
                        val pagingDataFlow: Flow<PagingData<Article>> = flow {
                            result.data?.let { pagingData ->
                                emit(pagingData)
                            }
                        }
                        _homeUiState.update { state ->
                            state.copy(
                                isLoading = false,
                                articles = pagingDataFlow.cachedIn(viewModelScope) // see notes below
                            )
                        }
                    }

                    is Result.Error -> {
                        _homeUiState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = result.message ?: "Unexpeted Error occurred"
                            )
                        }
                    }

                    is Result.Loading -> {
                        _homeUiState.update { state ->
                            state.copy(
                                isLoading = true,
                            )
                        }
                    }
                }

            }.launchIn(viewModelScope)

    }
}

// Imp note
/*We need to use cachedIn for paging data because if we don't use then we get following error
* When user trie to navigate back from details screen to home screen, it gives following error
 java.lang.IllegalStateException: Attempt to collect twice from pageEventFlow, which is an illegal operation. Did you forget to call Flow<PagingData<*>>.cachedIn(coroutineScope)?
*
* Since from UseCase we are returning Floe<Result<PagingData<Article>>> we have to add cachedIn while updating uiState
* If you see SearchViewModel and SearchNewsUseCase I'm returning Flow<PagingData<Article>> and directly using cachedIn(viewModel).
* */
