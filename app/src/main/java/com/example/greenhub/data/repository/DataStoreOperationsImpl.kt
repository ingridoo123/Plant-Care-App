package com.example.greenhub.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.greenhub.domain.repository.DataStoreOperations
import com.example.greenhub.util.Constants.PREFERENCES_KEY
import com.example.greenhub.util.Constants.PREFERENCES_KEY2
import com.example.greenhub.util.Constants.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context): DataStoreOperations {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = PREFERENCES_KEY)
        val loginKey = booleanPreferencesKey(name = PREFERENCES_KEY2)
    }


    private val dataStore = context.dataStore


    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit {
            it[PreferencesKey.onBoardingKey] = completed
        }
    }

    override suspend fun saveLoginState(completed: Boolean) {
        dataStore.edit {
            it[PreferencesKey.loginKey] = completed
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }

    override fun readLoginState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val loginState = preferences[PreferencesKey.loginKey] ?: false
                loginState
            }
    }

}