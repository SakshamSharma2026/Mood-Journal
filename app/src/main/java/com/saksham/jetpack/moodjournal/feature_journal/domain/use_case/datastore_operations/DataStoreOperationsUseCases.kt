package com.saksham.jetpack.moodjournal.feature_journal.domain.use_case.datastore_operations

data class DataStoreOperationsUseCases(
    val readOnBoarding: ReadOnBoardingUseCase,
    val saveOnBoarding: SaveOnBoardingUseCase,
    val readUserNameUseCase: ReadUserNameUseCase,
    val saveUserNameUserCase: SaveUserNameUserCase,
    val readFirstEntryUseCase: ReadFirstEntryUseCase,
    val saveFirstEntryUseCase: SaveFirstEntryUseCase
)