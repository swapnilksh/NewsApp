package com.example.newsapp.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.R
import com.example.newsapp.common.ResourceProvider
import com.example.newsapp.data.PageData
import com.example.newsapp.domain.usecase.OnBoardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    val onBoardingUseCase: OnBoardingUseCase
) : ViewModel() {

    fun setOnBoardingCompleted() {
        viewModelScope.launch {
            onBoardingUseCase.saveOnBoardingCompletion(true)
        }
    }

    // Ideally this should be fetched form data model.
    fun getPageList() = listOf(
        PageData(
            title = "Lorem Ipsum is simply dummy",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            imageResource = R.drawable.onboarding1
        ),
        PageData(
            title = "Lorem Ipsum is simply dummy",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            imageResource = R.drawable.onboarding2
        ),
        PageData(
            title = "Lorem Ipsum is simply dummy",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            imageResource = R.drawable.onboarding3
        )
    )
}