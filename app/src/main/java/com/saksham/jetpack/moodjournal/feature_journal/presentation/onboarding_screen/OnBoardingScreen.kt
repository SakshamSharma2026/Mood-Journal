package com.saksham.jetpack.moodjournal.feature_journal.presentation.onboarding_screen

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saksham.jetpack.moodjournal.R
import com.saksham.jetpack.moodjournal.feature_journal.domain.util.datastore.DataStoreOperationsEvent
import com.saksham.jetpack.moodjournal.feature_journal.navigation.Screen
import com.saksham.jetpack.moodjournal.feature_journal.presentation.DataStoreOperationsViewModel
import com.saksham.jetpack.moodjournal.feature_journal.util.sp
import com.saksham.jetpack.moodjournal.ui.theme.PeachSorbet
import com.saksham.jetpack.moodjournal.ui.theme.Violet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(
    componentActivity: ComponentActivity,
    navController: NavController,
    viewModel: DataStoreOperationsViewModel = hiltViewModel()
) {

    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val textFieldColors = TextFieldDefaults.textFieldColors(
        disabledTextColor = contentColorFor(Color.Transparent),
        cursorColor = contentColorFor(Color.Black),
        errorCursorColor = contentColorFor(Color.Transparent),
        focusedIndicatorColor = Violet,
        unfocusedIndicatorColor = Color.Black,
        disabledIndicatorColor = Color.Transparent,
        containerColor = Color.Transparent
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PeachSorbet)
    ) {

        Card(
            modifier = Modifier
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .clip(CircleShape)
                .clickable {
                    navController.navigateUp()
                }, colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                "Back Button",
                tint = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.6f)
                .align(Alignment.Center),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(id = R.drawable.panda_bear),
                    contentDescription = null
                )
                Text(text = stringResource(R.string.what_should_we_call_you))
                sp(value = 10)
                OutlinedTextField(
                    colors = textFieldColors,
                    placeholder = { Text(text = stringResource(R.string.enter_your_first_name)) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    value = text, onValueChange = { text = it })
                Button(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth(),
                    onClick = {
                        if (text.isNotEmpty()) {
                            viewModel.onEvent(DataStoreOperationsEvent.SaveOnBoardingCompleted(true))
                            viewModel.onEvent(DataStoreOperationsEvent.SaveUserName(text))
                            keyboardController?.hide()
                            navController.popBackStack()
                            navController.navigate(Screen.HomeScreen.route)
                        } else {
                            Toast.makeText(
                                componentActivity.applicationContext,
                                "Enter your name",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Violet)
                ) {
                    Text(text = stringResource(R.string.continue_btn))
                }
            }
        }
    }


}