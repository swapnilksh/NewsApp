package com.example.newsapp.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.domain.usecase.OnBoardingUseCase
import com.example.newsapp.navigation.screen.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
     onBoardingUseCase: OnBoardingUseCase
): ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Screen.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    init {
        onBoardingUseCase.getOnBoardingStatus().onEach { isOnBoardingCompleted ->
            if(isOnBoardingCompleted) {
                // Condition to directly navigate to home screen
                _startDestination.value = Screen.NewsNavigation.route
            } else {
                _startDestination.value = Screen.AppStartNavigation.route
            }
            delay(200) //Without this delay, the onBoarding screen will show for a momentum.
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}