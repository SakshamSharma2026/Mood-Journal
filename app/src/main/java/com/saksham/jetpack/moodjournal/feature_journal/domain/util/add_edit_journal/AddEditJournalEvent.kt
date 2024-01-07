package com.saksham.jetpack.moodjournal.feature_journal.domain.util.add_edit_journal

import android.net.Uri
import androidx.compose.ui.focus.FocusState
import java.io.File

sealed class AddEditJournalEvent {
    data class EnteredPrompt(val value: String = " ") : AddEditJournalEvent()
    data class EnteredContent(val value: String) : AddEditJournalEvent()
    data class ChangeDate(val value: String) : AddEditJournalEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditJournalEvent()
    data class ChangeColor(val color: Int) : AddEditJournalEvent()
    data class SelectImageUri(val uri: Uri = Uri.EMPTY) : AddEditJournalEvent()
    data class SaveAudioFilePath(val file: File? = null) : AddEditJournalEvent()
    data object SaveJournal : AddEditJournalEvent()
    data object ToggleDatePickerDialog : AddEditJournalEvent()
    data object ToggleVoiceRecorderSheet : AddEditJournalEvent()
    data object ToggleStartStopRecording : AddEditJournalEvent()
    data object ToggleAudioPlayer : AddEditJournalEvent()
}