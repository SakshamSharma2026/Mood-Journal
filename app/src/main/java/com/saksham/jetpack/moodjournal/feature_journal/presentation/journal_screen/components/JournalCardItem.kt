package com.saksham.jetpack.moodjournal.feature_journal.presentation.journal_screen.components

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.saksham.jetpack.moodjournal.feature_journal.domain.model.JournalDataResponse
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.journal.JournalEvent
import com.saksham.jetpack.moodjournal.feature_journal.navigation.Screen
import com.saksham.jetpack.moodjournal.feature_journal.presentation.journal_screen.JournalViewModel
import com.saksham.jetpack.moodjournal.feature_journal.util.shareContent
import com.saksham.jetpack.moodjournal.feature_journal.util.hsp


fun journalData(
    data: JournalDataResponse, isEdit: Boolean
): String {
    return Screen.AddEditJournalScreen.route + "?journalId=${data.id}&journalColor=${data.color}&isJournalEdit=${isEdit}"
}

@Composable
fun CardViewItem(
    activity: ComponentActivity,
    navController: NavController,
    data: JournalDataResponse,
    modifier: Modifier = Modifier,
    journalViewModel: JournalViewModel = hiltViewModel()
) {

    val columnSize = if (data.imageUri != Uri.EMPTY) {
        0.6f
    } else {
        1f
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                navController.navigate(journalData(data, false))
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(data.color),
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
            hsp(value = 10)
            Row(
                modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier.fillMaxWidth(columnSize)) {
                    if (data.title.isNotBlank()) {
                        Text(
                            text = data.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    Text(
                        text = data.content,
                        fontSize = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (data.imageUri != Uri.EMPTY) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = data.imageUri)
                                .build(),
                            filterQuality = FilterQuality.Medium
                        ),
                        contentDescription = null,
                        modifier = modifier
                            .width(80.dp)
                            .height(80.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }
            hsp(value = 10)
            /*if (data.audioFilePath != null) {
                Card(
                    modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.sound_wave),
                            modifier = modifier.size(120.dp),
                            contentDescription = null
                        )
                        Text(text = "Audio : ${data.audioDuration}", fontSize = 12.sp)
                    }

                }
            }*/
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = data.date, fontSize = 10.sp)
                CustomDropDownMenu(onEdit = {
                    navController.navigate(journalData(data, true))
                }, onDelete = {
                    journalViewModel.onEvent(JournalEvent.DeleteJournal(data))
                }, onShare = {
                    shareContent(activity, data.title, data.content)
                })
            }
        }
    }
}
