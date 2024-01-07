package com.saksham.jetpack.moodjournal.feature_journal.presentation.journal_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saksham.jetpack.moodjournal.feature_journal.domain.use_case.journal.JournalUseCases
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.journal.JournalEvent
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.journal.JournalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
    data object SaveJournal : UiEvent()
}

@HiltViewModel
class JournalViewModel @Inject constructor(private val journalUseCases: JournalUseCases) :
    ViewModel() {


    private val _state = mutableStateOf(JournalState())
    val state: State<JournalState> = _state


    private var getJournalJob: Job? = null

    init {
        getJournal("")
    }

    fun onEvent(event: JournalEvent) {
        when (event) {
            is JournalEvent.DeleteJournal -> {
                viewModelScope.launch {
                    journalUseCases.deleteJournal(event.journalDataResponse)
                }
            }

            is JournalEvent.SearchInList -> {
                getJournal(event.searchKeyWord)
            }
        }
    }

    private fun getJournal(searchKeyword: String) {
        getJournalJob?.cancel()
        getJournalJob = journalUseCases.getJournals()
            .map { journals ->
                if (searchKeyword.isNotEmpty()) {
                    journals.filter { journal ->
                        journal.title.contains(searchKeyword, ignoreCase = true) ||
                                journal.content.contains(searchKeyword, ignoreCase = true)
                    }
                } else {
                    journals
                }
            }
            .onEach { filteredJournals ->
                _state.value = state.value.copy(journal = filteredJournals)
                if (filteredJournals.isEmpty() && searchKeyword.isNotEmpty()) {
                    _state.value = state.value.copy(noResultFound = true)
                } else {
                    _state.value = state.value.copy(noResultFound = false)
                }
            }
            .launchIn(viewModelScope)


    }
}