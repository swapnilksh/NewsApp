package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.PreferenceManager
import javax.inject.Inject

class SaveOnBoardingCompletion @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    suspend operator fun invoke(isCompleted: Boolean) {
        preferenceManager.saveOnBoardingState(isCompleted = isCompleted)
    }
}
