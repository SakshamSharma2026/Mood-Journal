package com.saksham.jetpack.moodjournal.feature_journal.domain.util.journal

import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse

data class JournalState(
    val journal: List<JournalDataResponse> = emptyList(),
    val filteredList: List<JournalDataResponse> = emptyList(),
    val noResultFound: Boolean = false,
)