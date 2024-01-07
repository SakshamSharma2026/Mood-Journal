package com.saksham.jetpack.moodjournal.feature_journal.presentation.add_edit_journal.components


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.saksham.jetpack.moodjournal.ui.theme.ShimmerGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSearchBar(modifier: Modifier = Modifier, onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val textFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = ShimmerGray,
        disabledTextColor = contentColorFor(Color.Transparent),
        cursorColor = contentColorFor(Color.Black),
        errorCursorColor = contentColorFor(Color.Transparent),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )

    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearch(it)
        },
        placeholder = { Text("Search") },
        colors = textFieldColors,
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        modifier = modifier
            .height(50.dp)
            .clip(shape = RoundedCornerShape(12.dp)),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
    )
}