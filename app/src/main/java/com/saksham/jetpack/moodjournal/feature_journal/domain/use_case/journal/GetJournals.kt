package com.saksham.jetpack.moodjournal.feature_journal.domain.use_case.journal

import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import com.saksham.jetpack.moodjournal.feature_journal.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow

class GetJournals(private val repository: JournalRepository) {

    operator fun invoke(): Flow<List<JournalDataResponse>> {
        return repository.getJournal()
    }


}

