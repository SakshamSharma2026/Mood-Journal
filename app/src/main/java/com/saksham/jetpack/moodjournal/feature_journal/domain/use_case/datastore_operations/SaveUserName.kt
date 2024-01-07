package com.saksham.jetpack.moodjournal.feature_journal.domain.use_case.datastore_operations

import com.saksham.jetpack.moodjournal.feature_journal.domain.repository.DataStoreOperationsRepository

class SaveUserNameUserCase(
    private val dataStoreOperationsRepository: DataStoreOperationsRepository
) {
    suspend operator fun invoke(userName: String) {
        dataStoreOperationsRepository.saveUserNameState(userName)
    }
}