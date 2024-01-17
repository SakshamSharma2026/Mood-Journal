package com.saksham.jetpack.moodjournal.feature_journal.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.saksham.jetpack.moodjournal.feature_journal.navigation.SetupNavGraph
import com.saksham.jetpack.moodjournal.ui.theme.InnerCanvasTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        //requiredAudioPermission(this)
        setContent {
            InnerCanvasTheme {
                val navController = rememberNavController()
                SetupNavGraph(componentActivity = this, navController = navController)
            }
        }
    }
}





