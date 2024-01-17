package com.saksham.jetpack.moodjournal.feature_journal.presentation.settings

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saksham.jetpack.moodjournal.R
import com.saksham.jetpack.moodjournal.feature_journal.navigation.Screen
import com.saksham.jetpack.moodjournal.feature_journal.presentation.DataStoreOperationsViewModel
import com.saksham.jetpack.moodjournal.feature_journal.util.getAppVersion
import com.saksham.jetpack.moodjournal.feature_journal.util.openBrowser
import com.saksham.jetpack.moodjournal.feature_journal.util.rateApp
import com.saksham.jetpack.moodjournal.feature_journal.util.sendFeedbackByEmail
import com.saksham.jetpack.moodjournal.feature_journal.util.shareApp
import com.saksham.jetpack.moodjournal.feature_journal.util.hsp
import com.saksham.jetpack.moodjournal.ui.theme.ActivityBg
import com.saksham.jetpack.moodjournal.ui.theme.ShimmerGray
import com.saksham.jetpack.moodjournal.ui.theme.Violet


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    componentActivity: ComponentActivity,
    navController: NavController,
    dataStoreOperationsViewModel: DataStoreOperationsViewModel = hiltViewModel(),

    ) {

    val dataStoreState by dataStoreOperationsViewModel.state.collectAsState()

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = 20.dp,
                    end = 20.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                hsp(value = 20)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        "Back Button",
                        tint = Color.Black,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                    Text(
                        text = "Profile & Settings",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                hsp(value = 30)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(1.dp, Violet, CircleShape)
                            .size(40.dp)
                            .background(Violet)
                            .clickable {
                                navController.navigate(Screen.SettingsScreen.route)
                            }, contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painterResource(id = R.drawable.profile),
                            null,
                            tint = Color.White,
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = dataStoreState.userName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    )
                }
                hsp(value = 20)
                Card(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .border(1.dp, ShimmerGray, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            sendFeedbackByEmail(componentActivity)
                        },
                    colors = CardDefaults.cardColors(containerColor = ActivityBg)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp)
                    ) {
                        // First Icon
                        Row {
                            Icon(
                                imageVector = Icons.Default.ThumbUp,
                                tint = Violet,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 20.dp, start = 10.dp)
                                    .width(24.dp)
                            )

                            // Text
                            Text(
                                text = stringResource(R.string.share_feedback),
                            )
                        }


                        // Second Icon
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.Gray,// Optional tint color
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .width(24.dp)

                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, ShimmerGray, RoundedCornerShape(8.dp))
                        .clickable {
                            rateApp(componentActivity)
                        },
                    colors = CardDefaults.cardColors(containerColor = ActivityBg)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp)
                    ) {
                        // First Icon
                        Row {
                            Icon(
                                imageVector = Icons.Default.Star,
                                tint = Violet,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 20.dp, start = 10.dp)
                                    .width(24.dp)

                            )

                            // Text
                            Text(
                                text = stringResource(R.string.rate_mood_journal_app),
                            )
                        }


                        // Second Icon
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.Gray, // Optional tint color
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .width(24.dp)

                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, ShimmerGray, RoundedCornerShape(8.dp))
                        .clickable {
                            openBrowser(componentActivity)
                        },
                    colors = CardDefaults.cardColors(containerColor = ActivityBg)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp)
                    ) {
                        // First Icon
                        Row {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                tint = Violet,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 20.dp, start = 10.dp)
                                    .width(24.dp)

                            )

                            // Text
                            Text(
                                text = stringResource(R.string.privacy_policy),
                            )
                        }


                        // Second Icon
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.Gray, // Optional tint color
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .width(24.dp)

                        )
                    }
                }


                Card(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, ShimmerGray, RoundedCornerShape(8.dp))
                        .clickable {
                            shareApp(componentActivity)
                        },
                    colors = CardDefaults.cardColors(containerColor = ActivityBg)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp)
                    ) {
                        // First Icon
                        Row {
                            Icon(
                                imageVector = Icons.Default.Face,
                                tint = Violet,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 20.dp, start = 10.dp)
                                    .width(24.dp)

                            )

                            // Text
                            Text(
                                text = stringResource(R.string.invite_friends_family),
                            )
                        }


                        // Second Icon
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.Gray, // Optional tint color
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .width(24.dp)

                        )
                    }
                }

            }

            Box(
                modifier = Modifier
                    .padding(vertical = 30.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.consolecraft_technologies),
                        fontSize = 12.sp
                    )
                    Text(text = "v ${getAppVersion(componentActivity)}", fontSize = 12.sp)
                }

            }

        }

    }

}