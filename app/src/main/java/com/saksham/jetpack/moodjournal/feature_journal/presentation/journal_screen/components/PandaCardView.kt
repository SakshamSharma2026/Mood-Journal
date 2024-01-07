package com.saksham.jetpack.moodjournal.feature_journal.presentation.journal_screen.components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.saksham.jetpack.moodjournal.R
import com.saksham.jetpack.moodjournal.feature_journal.navigation.Screen
import com.saksham.jetpack.moodjournal.feature_journal.util.getCurrentDateTime
import com.saksham.jetpack.moodjournal.ui.theme.Buttercream
import com.saksham.jetpack.moodjournal.ui.theme.Violet


@Composable
fun PandaCardView(activity: ComponentActivity, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.6f)
                .clip(RoundedCornerShape(corner = CornerSize(22.dp)))
                .clickable {
                    navController.navigate(Screen.AddEditJournalScreen.route)
                },
            shape = RoundedCornerShape(corner = CornerSize(22.dp)),
            colors = CardDefaults.cardColors(containerColor = Buttercream),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.panda_bear),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .height(100.dp)
                )
                Text(
                    text = getCurrentDateTime(), textAlign = TextAlign.Center, fontSize = 12.sp
                )
                Text(
                    text = "Let's help you write your first entry!",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(1.dp, Violet, CircleShape)
                        .size(50.dp)
                        .background(Violet), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward, null, tint = Color.White
                    )
                }
            }
        }
    }
}