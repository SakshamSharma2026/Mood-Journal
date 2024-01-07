package com.saksham.jetpack.moodjournal.feature_journal.domain.use_case.journal

import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import com.saksham.jetpack.moodjournal.feature_journal.domain.repository.JournalRepository

class DeleteJournal(private val repository: JournalRepository) {

    suspend operator fun invoke(journalDataResponse: JournalDataResponse) {
        repository.deleteJournal(journalDataResponse)
    }
}