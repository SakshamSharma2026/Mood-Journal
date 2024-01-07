package com.saksham.jetpack.moodjournal.feature_journal.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperationsRepository {
    suspend fun saveOnBoardingState(completed: Boolean)

    suspend fun saveUserNameState(userName: String)

    suspend fun saveFirstEntryState(completed: Boolean)

    fun readFirstEntryState(): Flow<Boolean>

    fun readOnBoardingState(): Flow<Boolean>

    fun readUserNameState(): Flow<String>
}