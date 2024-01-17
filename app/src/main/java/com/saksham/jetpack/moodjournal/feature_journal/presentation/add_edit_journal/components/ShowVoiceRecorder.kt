package com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saksham.jetpack.moodjournal.R
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.add_edit_journal.AddEditJournalEvent
import com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.AddEditJournalViewModel
import com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.util.AndroidAudioRecorder
import com.saksham.jetpack.moodjournal.feature_journal.util.formatDuration
import com.saksham.jetpack.moodjournal.feature_journal.util.generateRecordingName
import com.saksham.jetpack.moodjournal.feature_journal.util.hsp
import com.saksham.jetpack.moodjournal.ui.theme.Violet
import com.saksham.jetpack.moodjournal.ui.theme.fontFamily
import kotlinx.coroutines.CoroutineScope
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowVoiceRecorder(
    isSheetOpen: Boolean,
    viewModel: AddEditJournalViewModel,
    scope: CoroutineScope,
    onDismiss: () -> Unit
) {

    val context = LocalContext.current
    val state = viewModel.state.value

    var recordingDuration by remember { mutableStateOf(0) }
    var audioFile by remember { mutableStateOf<File?>(null) }

    val recorder by lazy {
        AndroidAudioRecorder(context)
    }

    val modalBottomSheetState = rememberModalBottomSheetState()


    if (isSheetOpen)
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = {
                recordingDuration = 0
                audioFile = null
                onDismiss()
            },
            sheetState = modalBottomSheetState,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.headphones),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                hsp(value = 20)
                Text(
                    text = stringResource(R.string.record_voice_note),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                hsp(value = 5)
                Text(
                    text = stringResource(R.string.tap_the_below_button_when_ready),
                    fontSize = 12.sp
                )
                hsp(value = 20)

                if (state.isVoiceRecorderActive) {
                    Text(text = formatDuration(viewModel.recordingDuration.value), fontSize = 18.sp)
                    hsp(value = 20)
                }


                val outputDir =
                    context.externalCacheDir?.absolutePath ?: context.cacheDir.absolutePath

                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Violet),
                    onClick = {
                        viewModel.onEvent(AddEditJournalEvent.ToggleStartStopRecording)
                        if (state.isVoiceRecorderActive) {
                            recorder.stop()
                            audioFile?.let {
                                viewModel.onEvent(AddEditJournalEvent.SaveAudioFilePath(it))
                            }
                            onDismiss()
                        } else {
                            File(generateRecordingName(outputDir)).also {
                                recorder.start(it)
                                audioFile = it
                            }
                        }
                    }) {
                    Text(
                        text = if (state.isVoiceRecorderActive) stringResource(R.string.save_recoding) else stringResource(
                            R.string.start_recoding
                        ),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontFamily = fontFamily,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Icon(
                        painter = if (state.isVoiceRecorderActive) painterResource(id = R.drawable.baseline_done_24) else painterResource(
                            id = R.drawable.microphone_icon
                        ),
                        null,
                        modifier = Modifier.size(20.dp),
                    )
                }
                hsp(value = 20)
            }

        }

}