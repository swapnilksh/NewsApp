package com.example.newsapp.domain.repository.onboarding

import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {
    suspend fun saveOnBoardingState(isCompleted: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
}