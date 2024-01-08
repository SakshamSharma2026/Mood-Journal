package com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saksham.jetpack.moodjournal.feature_journal.domain.model.InvalidJournalException
import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import com.saksham.jetpack.moodjournal.feature_journal.domain.use_case.journal.JournalUseCases
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.add_edit_journal.AddEditJournalEvent
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.add_edit_journal.AddEditJournalState
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.journal.JournalTextFieldState
import com.saksham.jetpack.moodjournal.feature_journal.presentation.journal_screen.UiEvent
import com.saksham.jetpack.moodjournal.feature_journal.util.Constants.journalId
import com.saksham.jetpack.moodjournal.feature_journal.util.formatDuration
import com.saksham.jetpack.moodjournal.feature_journal.util.getCurrentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class AddEditJournalViewModel @Inject constructor(
    private val useCases: JournalUseCases,

    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _state = mutableStateOf(AddEditJournalState())
    val state: State<AddEditJournalState> = _state

    private val _journalPrompt = mutableStateOf(
        JournalDataResponse.journalPrompts.random()
    )
    val journalPrompt: State<String> = _journalPrompt

    private val _journalContent =
        mutableStateOf(JournalTextFieldState(hint = "Tap here to start writing..."))
    val journalContent: State<JournalTextFieldState> = _journalContent

    private val _journalColor = mutableStateOf(JournalDataResponse.journalColors.random().toArgb())
    val journalColor: State<Int> = _journalColor


    private val _journalImageUri = mutableStateOf(Uri.EMPTY)
    val journalImageUri: State<Uri> = _journalImageUri

    private val _journalAudioFilePath = mutableStateOf<File?>(null)
    val journalAudioFilePath: State<File?> = _journalAudioFilePath

    private val _recordingDuration = mutableStateOf(0)
    val recordingDuration: State<Int> = _recordingDuration


    private val _journalDate = mutableStateOf(getCurrentDateTime())
    val journalDate: State<String> = _journalDate

    private val _journalAudioDuration = mutableStateOf("")
    val journalAudioDuration: State<String> = _journalAudioDuration

    private var currentJournalId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>(journalId)?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    useCases.getJournalById(id)?.also { journal ->
                        currentJournalId = journal.id
                        _journalPrompt.value = journal.title
                        _journalContent.value = journalContent.value.copy(
                            text = journal.content, isHintVisible = false
                        )
                        _journalColor.value = journal.color
                        _journalDate.value = journal.date
                        _journalImageUri.value = journal.imageUri
                        _journalAudioDuration.value = journal.audioDuration
                        if (journal.audioFilePath != null) {
                            _journalAudioFilePath.value = journal.audioFilePath
                        } else {
                            _journalAudioFilePath.value = null
                        }
                    }
                }
            }
        }


    }


    fun onEvent(event: AddEditJournalEvent) {
        when (event) {
            is AddEditJournalEvent.ChangeColor -> {
                _journalColor.value = event.color
            }

            is AddEditJournalEvent.ChangeContentFocus -> {
                _journalContent.value =
                    journalContent.value.copy(isHintVisible = !event.focusState.isFocused && journalContent.value.text.isBlank())
            }

            is AddEditJournalEvent.EnteredContent -> {
                _journalContent.value = journalContent.value.copy(text = event.value)

            }

            is AddEditJournalEvent.EnteredPrompt -> {
                _journalPrompt.value = event.value
            }

            is AddEditJournalEvent.ChangeDate -> {
                _journalDate.value = event.value
            }

            is AddEditJournalEvent.SelectImageUri -> {
                _journalImageUri.value = event.uri
            }


            AddEditJournalEvent.SaveJournal -> {
                viewModelScope.launch {
                    try {
                        useCases.addJournal(
                            JournalDataResponse(
                                title = journalPrompt.value,
                                content = journalContent.value.text,
                                date = journalDate.value,
                                color = journalColor.value,
                                imageUri = journalImageUri.value,
                                audioFilePath = journalAudioFilePath.value,
                                audioDuration = journalAudioDuration.value,
                                id = currentJournalId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveJournal)
                    } catch (e: InvalidJournalException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save journal"
                            )
                        )
                    }
                }
            }

            AddEditJournalEvent.ToggleDatePickerDialog -> {
                _state.value =
                    state.value.copy(isDatePickerDialogVisible = !state.value.isDatePickerDialogVisible)
            }

            AddEditJournalEvent.ToggleVoiceRecorderSheet -> {
                _state.value =
                    state.value.copy(isVoiceRecorderSheetVisible = !state.value.isVoiceRecorderSheetVisible)
            }

            is AddEditJournalEvent.SaveAudioFilePath -> {
                _journalAudioFilePath.value = event.file
            }

            AddEditJournalEvent.ToggleStartStopRecording -> {
                _state.value =
                    state.value.copy(isVoiceRecorderActive = !state.value.isVoiceRecorderActive)
                if (state.value.isVoiceRecorderActive) {
                    startRecordingCoroutine()
                } else {
                    stopRecording()
                }
            }

            AddEditJournalEvent.ToggleAudioPlayer -> {
                _state.value = state.value.copy(isAudioPlaying = !state.value.isAudioPlaying)
            }
        }
    }

    private fun startRecordingCoroutine() {
        viewModelScope.launch {
            while (isActive) {
                delay(1000)
                _recordingDuration.value++
            }
        }
    }

    private fun stopRecording() {
        viewModelScope.coroutineContext.cancelChildren() // Cancels all child coroutines

        _journalAudioDuration.value = formatDuration(recordingDuration.value)
        _recordingDuration.value = 0
    }
}
