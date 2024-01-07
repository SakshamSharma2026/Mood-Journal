package com.saksham.jetpack.moodjournal.feature_journal.domain.util.datastore

sealed class DataStoreOperationsEvent {
    data class SaveOnBoardingCompleted(val completed: Boolean) : DataStoreOperationsEvent()
    data class SaveFirstEntryCompleted(val completed: Boolean) : DataStoreOperationsEvent()
    data class SaveUserName(val name: String) : DataStoreOperationsEvent()
}