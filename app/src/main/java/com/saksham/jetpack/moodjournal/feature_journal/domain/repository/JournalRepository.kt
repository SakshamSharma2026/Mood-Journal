package com.saksham.jetpack.moodjournal.feature_journal.domain.repository

import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import kotlinx.coroutines.flow.Flow

interface JournalRepository {

    fun getJournal(): Flow<List<JournalDataResponse>>

    suspend fun getJournalById(id: Int): JournalDataResponse?

    suspend fun insertJournal(journalDataResponse: JournalDataResponse)

    suspend fun deleteJournal(journalDataResponse: JournalDataResponse)

}