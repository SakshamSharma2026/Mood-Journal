package com.saksham.jetpack.moodjournal.feature_journal.presentation.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saksham.jetpack.moodjournal.R
import com.saksham.jetpack.moodjournal.feature_journal.navigation.Screen
import com.saksham.jetpack.moodjournal.feature_journal.presentation.DataStoreOperationsViewModel
import com.saksham.jetpack.moodjournal.ui.theme.Violet
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavController,
    dataStoreOperationsViewModel: DataStoreOperationsViewModel = hiltViewModel()
) {

    val state by dataStoreOperationsViewModel.state.collectAsState()


    LaunchedEffect(key1 = true) {
        delay(1000)
        navController.popBackStack()
        if (state.onBoardingCompleted) navController.navigate(Screen.HomeScreen.route)
        else navController.navigate(Screen.OnBoardingScreen.route)
    }


    val gradient = Brush.verticalGradient(
        0.1f to Color.Black,
        1f to Violet, startY = 0.0f,
        endY = 1800.0f
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(105.dp)
                .background(Color.White), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.applogo),
                modifier = Modifier
                    .size(100.dp)
                ,
                contentDescription = null
            )
        }



        Text(
            text = stringResource(R.string.mood_journal),
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = stringResource(R.string.use_the_mood_journal),
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
        )


    }
}