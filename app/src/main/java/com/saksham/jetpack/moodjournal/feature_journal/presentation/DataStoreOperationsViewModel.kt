package com.saksham.jetpack.moodjournal.feature_journal.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saksham.jetpack.moodjournal.feature_journal.domain.use_case.datastore_operations.DataStoreOperationsUseCases
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.datastore.DataStoreOperationsEvent
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.datastore.DataStoreOperationsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreOperationsViewModel @Inject constructor(
    private val useCases: DataStoreOperationsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(DataStoreOperationsState())
    val state: StateFlow<DataStoreOperationsState> = _state


    init {
        observeDataStoreValues()
    }

    private fun observeDataStoreValues() {
        viewModelScope.launch {
            useCases.readOnBoarding().collect { value ->
                _state.value = state.value.copy(onBoardingCompleted = value)
            }
        }
        viewModelScope.launch {
            useCases.readFirstEntryUseCase().collect { value ->
                _state.value = state.value.copy(firstEntryCompleted = value)
            }
        }

        viewModelScope.launch {
            useCases.readUserNameUseCase().collect { value ->
                _state.value = state.value.copy(userName = value)
            }
        }
    }


    fun onEvent(event: DataStoreOperationsEvent) {
        when (event) {
            is DataStoreOperationsEvent.SaveFirstEntryCompleted -> {
                if (!_state.value.firstEntryCompleted)
                    viewModelScope.launch(Dispatchers.IO) {
                        useCases.saveFirstEntryUseCase(event.completed)
                    }
            }

            is DataStoreOperationsEvent.SaveOnBoardingCompleted -> {
                viewModelScope.launch(Dispatchers.IO) {
                    useCases.saveOnBoarding(event.completed)
                }
            }

            is DataStoreOperationsEvent.SaveUserName -> {
                viewModelScope.launch(Dispatchers.IO) {
                    useCases.saveUserNameUserCase(event.name)
                }
            }
        }
    }
}