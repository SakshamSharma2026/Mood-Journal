package com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier, thickness: Dp = 1.dp
) {
    Box(
        modifier.run {
            fillMaxHeight()
                .width(thickness)
                .background(color = Color.Black)
        }
    )
}
