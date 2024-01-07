package com.saksham.jetpack.moodjournal.feature_journal.presentation.journal_screen

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.journal.JournalEvent
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.journal.JournalState
import com.saksham.jetpack.moodjournal.feature_journal.navigation.Screen
import com.saksham.jetpack.moodjournal.feature_journal.presentation.DataStoreOperationsViewModel
import com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.components.ShowSearchBar
import com.saksham.jetpack.moodjournal.feature_journal.presentation.journal_screen.components.CardViewItem
import com.saksham.jetpack.moodjournal.feature_journal.presentation.journal_screen.components.PandaCardView
import com.saksham.jetpack.moodjournal.ui.theme.Violet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    activity: ComponentActivity,
    navController: NavController,
    dataStoreOperationsViewModel: DataStoreOperationsViewModel = hiltViewModel(),
    viewModel: JournalViewModel = hiltViewModel()
) {


    val state by viewModel.state
    val dataStoreState by dataStoreOperationsViewModel.state.collectAsState()


    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Violet
                )
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = state.journal.isNotEmpty()) {
                ExtendedFloatingActionButton(
                    containerColor = Violet,
                    onClick = { navController.navigate(Screen.AddEditJournalScreen.route) },
                    icon = {
                        Icon(
                            Icons.Filled.Add, "Extended floating action button", tint = Color.White
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.write_entry),
                            color = Color.White
                        )
                    },
                    shape = CircleShape
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 20.dp, start = 12.dp, end = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "${dataStoreState.userName}'s Journal",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    )
                    Text(
                        text = stringResource(R.string.capture_your_life_s_moments),
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(1.dp, Violet, CircleShape)
                        .size(40.dp)
                        .background(Violet), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(id = R.drawable.profile),
                        null,
                        tint = Color.White,
                    )
                }
            }
            AnimatedVisibility(
                visible = dataStoreState.firstEntryCompleted
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowSearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    ) {
                        viewModel.onEvent(JournalEvent.SearchInList(it))
                    }
                }
            }

            VerticalList(activity, navController = navController, state)
        }
    }
}

@Composable
fun VerticalList(activity: ComponentActivity, navController: NavController, state: JournalState) {

    if (state.journal.isEmpty()) {
        if (state.noResultFound)
            Text(text = stringResource(R.string.no_result_found))
        else
            PandaCardView(navController)
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(state.journal, key = { item -> item.id!! }) { value ->
                CardViewItem(activity, navController, value)
            }
        }
    }

}