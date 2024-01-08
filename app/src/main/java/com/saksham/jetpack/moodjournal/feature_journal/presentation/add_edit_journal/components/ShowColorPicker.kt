package com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saksham.jetpack.moodjournal.R
import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.add_edit_journal.AddEditJournalEvent
import com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.AddEditJournalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowColorPicker(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    viewModel: AddEditJournalViewModel,
    journalBackgroundAnimatable: Animatable<Color, AnimationVector4D>,
    scope: CoroutineScope
) {

    BottomSheetScaffold(modifier = Modifier.pointerInput(Unit) {
        detectTapGestures(onTap = {
            scope.launch {
                if (bottomSheetScaffoldState.bottomSheetState.isVisible) {
                    bottomSheetScaffoldState.bottomSheetState.hide()
                }
            }
        })
    },
        sheetPeekHeight = 0.dp,
        sheetContainerColor = Color.White,
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.color),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp, bottom = 5.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    items(JournalDataResponse.journalColors) { color ->
                        val colorInt = color.toArgb()
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 1.dp,
                                color = if (viewModel.journalColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    journalBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt), animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditJournalEvent.ChangeColor(colorInt))
                            })

                    }

                }
            }

        }) {

    }

}


