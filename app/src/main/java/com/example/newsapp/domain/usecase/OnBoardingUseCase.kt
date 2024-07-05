package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.PreferenceManager
import javax.inject.Inject

data class OnBoardingUseCase(
    val getOnBoardingStatus: GetOnBoardingStatus,
    val saveOnBoardingCompletion: SaveOnBoardingCompletion
)