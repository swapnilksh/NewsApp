package com.example.newsapp.data.repository.onboarding

import com.example.newsapp.domain.PreferenceManager
import com.example.newsapp.domain.repository.onboarding.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager
) : OnBoardingRepository {
    override suspend fun saveOnBoardingState(isCompleted: Boolean) {
        preferenceManager.saveOnBoardingState(isCompleted)
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return preferenceManager.readOnBoardingState()
    }
}