package com.saksham.jetpack.moodjournal.feature_journal.domain.util.datastore

data class DataStoreOperationsState(
    val onBoardingCompleted: Boolean = false,
    val firstEntryCompleted: Boolean = false,
    val userName: String = ""
)