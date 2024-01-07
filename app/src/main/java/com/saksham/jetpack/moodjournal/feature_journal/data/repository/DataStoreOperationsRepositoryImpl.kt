package com.saksham.jetpack.moodjournal.feature_journal.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.saksham.jetpack.moodjournal.feature_journal.domain.repository.DataStoreOperationsRepository
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.FIRST_ENTRY_PREFERENCES_KEY
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.ONBOARDING_PREFERENCES_KEY
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.PREFERENCES_NAME
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.USER_NAME_PREFERENCES_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsRepositoryImpl(context: Context) : DataStoreOperationsRepository {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = ONBOARDING_PREFERENCES_KEY)
        val userNameKey = stringPreferencesKey(name = USER_NAME_PREFERENCES_KEY)
        val firstEntryKey = booleanPreferencesKey(name = FIRST_ENTRY_PREFERENCES_KEY)
    }

    private val dataStore = context.datastore

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    override suspend fun saveUserNameState(userName: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.userNameKey] = userName
        }
    }

    override suspend fun saveFirstEntryState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.firstEntryKey] = completed
        }
    }

    override fun readFirstEntryState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.firstEntryKey] ?: false
                onBoardingState
            }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }

    override fun readUserNameState(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                val userNameState = preferences[PreferencesKey.userNameKey] ?: ""
                userNameState
            }
    }
}