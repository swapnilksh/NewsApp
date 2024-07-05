package com.example.newsapp.domain

import kotlinx.coroutines.flow.Flow

interface PreferenceManager {
    suspend fun saveOnBoardingState(isCompleted: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
}