package com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.saksham.jetpack.moodjournal.JournalApp
import com.saksham.jetpack.moodjournal.R
import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.add_edit_journal.AddEditJournalEvent
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.datastore.DataStoreOperationsEvent
import com.saksham.jetpack.moodjournal.feature_journal.presentation.DataStoreOperationsViewModel
import com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.components.CustomHintTextField
import com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.components.DatePickerView
import com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.components.ShowColorPicker
import com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.components.ShowVoiceRecorder
import com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.components.VerticalDivider
import com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.util.AndroidAudioPlayer
import com.saksham.jetpack.moodjournal.feature_journal.presentation.journal_screen.UiEvent
import com.saksham.jetpack.moodjournal.feature_journal.util.sp
import com.saksham.jetpack.moodjournal.ui.theme.Violet
import com.saksham.jetpack.moodjournal.ui.theme.fontFamily
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditJournalScreen(
    componentActivity: ComponentActivity,
    navController: NavController,
    journalColor: Int,
    isEdit: Boolean,
    dataStoreOperationsViewModel: DataStoreOperationsViewModel = hiltViewModel(),
    viewModel: AddEditJournalViewModel = hiltViewModel()
) {

    val state by viewModel.state
    val dataStoreState by dataStoreOperationsViewModel.state.collectAsState()

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val titleState = viewModel.journalPrompt.value
    val contentState = viewModel.journalContent.value
    val dateState = viewModel.journalDate.value
    val imageUriState = viewModel.journalImageUri.value
    val audioFilePath = viewModel.journalAudioFilePath.value
    val audioDuration = viewModel.journalAudioDuration.value


    val photoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                uri?.let {
                    JournalApp.getUriPermission(it)
                    viewModel.onEvent(AddEditJournalEvent.SelectImageUri(it))
                }
            })

    val journalBackgroundAnimatable = remember {
        Animatable(Color(if (journalColor != -1) journalColor else viewModel.journalColor.value))
    }

    val player by lazy {
        AndroidAudioPlayer(context)
    }

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SaveJournal -> {
                    keyboardController?.hide()
                    navController.navigateUp()
                }

                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }

        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = journalBackgroundAnimatable.value,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding())
                .padding(top = it.calculateTopPadding())
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                navController.navigateUp()
                            }, colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Back Button",
                            tint = Color.Black,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    if (isEdit) {
                        AnimatedVisibility(
                            visible = contentState.text.isNotEmpty(),
                        ) {
                            Button(border = BorderStroke(1.dp, Color.Black),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                                onClick = {
                                    if (!dataStoreState.firstEntryCompleted) {
                                        dataStoreOperationsViewModel.onEvent(
                                            DataStoreOperationsEvent.SaveFirstEntryCompleted(
                                                true
                                            )
                                        )
                                    }
                                    viewModel.onEvent(AddEditJournalEvent.SaveJournal)
                                }) {
                                Icon(
                                    Icons.Filled.Done,
                                    null,
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Black
                                )
                                Text(
                                    text = stringResource(R.string.save),
                                    modifier = Modifier.padding(start = 10.dp)
                                )

                            }
                        }
                    }
                }
                sp(value = 20)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                if (isEdit)
                                    viewModel.onEvent(AddEditJournalEvent.ToggleDatePickerDialog)
                            }) {
                        Text(
                            text = dateState,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Icon(
                            Icons.Filled.KeyboardArrowDown, null, tint = Color.Black,
                        )
                    }
                }
                sp(value = 20)
                AnimatedVisibility(visible = titleState.isNotBlank()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            text = titleState,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                        )
                        AnimatedVisibility(visible = isEdit) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(20.dp)
                                    .background(Color.LightGray)
                                    .clickable {
                                        viewModel.onEvent(AddEditJournalEvent.EnteredPrompt())
                                    }, contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = Color.White
                                )
                            }
                        }

                    }
                }
                sp(value = 20)
                AnimatedVisibility(visible = contentState.text.isEmpty()) {
                    Button(
                        onClick = {
                            val list = JournalDataResponse.journalPrompts
                            val randomIndex = (list.indices).random()
                            viewModel.onEvent(AddEditJournalEvent.EnteredPrompt(list[randomIndex]))
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {

                        Text(
                            text = stringResource(R.string.new_prompt),
                            color = Violet,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        Icon(
                            Icons.Filled.Refresh,
                            null,
                            modifier = Modifier.size(20.dp),
                            tint = Violet
                        )
                    }
                }
                sp(value = 10)
                CustomHintTextField(
                    text = contentState.text,
                    hint = contentState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditJournalEvent.EnteredContent(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditJournalEvent.ChangeContentFocus(it))
                    },
                    isHintVisible = contentState.isHintVisible,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    enabled = isEdit,
                    modifier = Modifier.fillMaxHeight(0.3f)
                )

                AnimatedVisibility(visible = audioFilePath != null) {
                    Box {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Image(
                                    painter = if (state.isAudioPlaying) painterResource(id = R.drawable.stop) else painterResource(
                                        id = R.drawable.play_circle
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable {
                                            if (state.isAudioPlaying)
                                                player.stop()
                                            else
                                                audioFilePath?.let { it1 -> player.playFile(it1) }

                                            viewModel.onEvent(AddEditJournalEvent.ToggleAudioPlayer)
                                        }
                                )
                                Text(
                                    text = if (state.isAudioPlaying) stringResource(R.string.pause_audio) else stringResource(
                                        R.string.play_audio
                                    ),
                                    fontSize = 12.sp
                                )

                                Text(text = audioDuration, fontSize = 12.sp)
                            }

                        }
                        if (isEdit)
                            Box(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .clip(CircleShape)
                                    .size(20.dp)
                                    .background(Color.LightGray)
                                    .align(Alignment.TopEnd)
                                    .clickable {
                                        viewModel.onEvent(AddEditJournalEvent.SaveAudioFilePath())
                                    }, contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = Color.White
                                )
                            }
                    }
                }

                sp(value = 10)

                AnimatedVisibility(visible = imageUriState.toString().isNotEmpty()) {
                    Box {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = imageUriState
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.FillBounds,
                        )
                        if (isEdit)
                            Box(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clip(CircleShape)
                                    .size(20.dp)
                                    .background(Color.LightGray)
                                    .align(Alignment.TopEnd)
                                    .clickable {
                                        viewModel.onEvent(AddEditJournalEvent.SelectImageUri())
                                    }, contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = Color.White
                                )
                            }
                    }
                }


            }
            AnimatedVisibility(visible = isEdit, modifier = Modifier.align(Alignment.BottomStart)) {
                Card(
                    modifier = Modifier
                        .padding(vertical = 30.dp, horizontal = 12.dp)
                        .height(50.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        sp(value = 8)
                        Icon(painterResource(id = R.drawable.gallery_icon),
                            null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .clickable {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                })
                        VerticalDivider(modifier = Modifier.padding(horizontal = 15.dp))
                        Icon(painterResource(id = R.drawable.color_icon),
                            null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .clickable {
                                    scope.launch {
                                        bottomSheetScaffoldState.bottomSheetState.expand()
                                    }
                                })
                        /*        VerticalDivider(modifier = Modifier.padding(horizontal = 15.dp))
                                Icon(painterResource(id = R.drawable.microphone_icon),
                                    contentDescription = null,
                                    tint = Color.Black,

                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                            requiredAudioPermission(componentActivity)
                                            viewModel.onEvent(AddEditJournalEvent.ToggleVoiceRecorderSheet)
                                        })*/
                        sp(value = 8)
                    }
                }
            }

        }
    }

    ShowColorPicker(
        bottomSheetScaffoldState,
        viewModel = viewModel,
        journalBackgroundAnimatable = journalBackgroundAnimatable,
        scope
    )


    ShowVoiceRecorder(
        state.isVoiceRecorderSheetVisible, viewModel, scope
    ) {
        viewModel.onEvent(AddEditJournalEvent.ToggleVoiceRecorderSheet)
    }

    if (state.isDatePickerDialogVisible) DatePickerView({
        viewModel.onEvent(AddEditJournalEvent.ChangeDate(it))
    }, { viewModel.onEvent(AddEditJournalEvent.ToggleDatePickerDialog) })
}

















