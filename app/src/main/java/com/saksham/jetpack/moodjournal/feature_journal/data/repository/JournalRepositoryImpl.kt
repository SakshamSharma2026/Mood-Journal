package com.saksham.jetpack.moodjournal.feature_journal.data.repository

import com.saksham.jetpack.moodjournal.feature_journal.data.data_source.JournalDao
import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import com.saksham.jetpack.moodjournal.feature_journal.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow

class JournalRepositoryImpl(private val dao: JournalDao) : JournalRepository {

    override fun getJournal(): Flow<List<JournalDataResponse>> = dao.getJournal()

    override suspend fun getJournalById(id: Int): JournalDataResponse? = dao.getJournalById(id)

    override suspend fun insertJournal(journalDataResponse: JournalDataResponse) = dao.insertJournal(journalDataResponse)

    override suspend fun deleteJournal(journalDataResponse: JournalDataResponse) = dao.deleteJournal(journalDataResponse)
}