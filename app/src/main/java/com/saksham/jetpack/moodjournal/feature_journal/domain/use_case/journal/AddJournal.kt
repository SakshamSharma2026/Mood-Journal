package com.saksham.jetpack.moodjournal.feature_journal.domain.use_case.journal

import com.saksham.jetpack.moodjournal.feature_journal.domain.model.InvalidJournalException
import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import com.saksham.jetpack.moodjournal.feature_journal.domain.repository.JournalRepository

class AddJournal(private val repository: JournalRepository) {


    @Throws(InvalidJournalException::class)
    suspend operator fun invoke(journalDataResponse: JournalDataResponse) {
        if (journalDataResponse.content.isBlank()) {
            throw InvalidJournalException("The content of the journal can't be empty.")
        }
        repository.insertJournal(journalDataResponse)
    }


}