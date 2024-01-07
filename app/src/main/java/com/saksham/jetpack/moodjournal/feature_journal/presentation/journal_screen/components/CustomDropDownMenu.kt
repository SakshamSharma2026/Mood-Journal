package com.saksham.jetpack.moodjournal.feature_journal.presentation.journal_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.saksham.jetpack.moodjournal.ui.theme.Violet

@Composable
fun CustomDropDownMenu(onEdit: () -> Unit, onDelete: () -> Unit, onShare: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .wrapContentSize(Alignment.TopEnd)
    ) {

        Icon(
            modifier = Modifier
                .rotate(90f)
                .clickable {
                    expanded = !expanded
                },
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More"
        )
        DropdownMenu(
            modifier = Modifier.background(Color.White),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = {
                    onEdit()
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    openDialog = true
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Share") },
                onClick = {
                    onShare()
                    expanded = false
                }
            )
        }
    }

    if (openDialog)
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = {
                openDialog = false
            },
            title = {
                Text(text = "Delete this entry?")
            },
            text = {
                Text("You will not be able to undo this action.")
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Violet),
                    onClick = {
                        openDialog = false
                        onDelete()
                    }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    border = BorderStroke(1.dp, Color.Black),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                    onClick = {
                        openDialog = false

                    }) {
                    Text("Cancel", color = Color.Black)
                }
            }
        )
}



