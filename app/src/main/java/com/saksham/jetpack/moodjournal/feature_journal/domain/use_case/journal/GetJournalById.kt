package com.saksham.jetpack.moodjournal.feature_journal.domain.use_case.journal

import com.saksham.jetpack.moodjournal.feature_journal.domain.model.InvalidJournalException
import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import com.saksham.jetpack.moodjournal.feature_journal.domain.repository.JournalRepository

class GetJournalById(private val repository: JournalRepository) {

    @Throws(InvalidJournalException::class)
    suspend operator fun invoke(id: Int): JournalDataResponse? = repository.getJournalById(id)
}