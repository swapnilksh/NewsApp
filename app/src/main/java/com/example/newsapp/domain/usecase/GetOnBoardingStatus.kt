package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.PreferenceManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnBoardingStatus @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    operator fun invoke(): Flow<Boolean> =
        preferenceManager.readOnBoardingState()

}