package com.example.newsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.newsapp.domain.PreferenceManager
import com.example.newsapp.util.Constants
import com.example.newsapp.util.Constants.APP_ENTRY
import com.example.newsapp.util.Constants.USER_ON_BOARDING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_ON_BOARDING)

class PreferenceManagerImpl(private val context: Context) : PreferenceManager {

    private object PreferenceKeys {
        val APP_ENTRY = booleanPreferencesKey(Constants.APP_ENTRY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveOnBoardingState(isCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.APP_ENTRY] = isCompleted
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            val onBoardingState = preferences[PreferenceKeys.APP_ENTRY] ?: false
            onBoardingState
        }
    }
}