package com.saksham.jetpack.moodjournal.feature_journal.domain.use_case.datastore_operations

import com.saksham.jetpack.moodjournal.feature_journal.domain.repository.DataStoreOperationsRepository
import kotlinx.coroutines.flow.Flow

class ReadFirstEntryUseCase(
    private val dataStoreOperationsRepository: DataStoreOperationsRepository
) {
    operator fun invoke(): Flow<Boolean> =
        dataStoreOperationsRepository.readFirstEntryState()
}