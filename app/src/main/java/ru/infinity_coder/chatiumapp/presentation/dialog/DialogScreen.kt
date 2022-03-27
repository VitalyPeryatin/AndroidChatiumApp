package ru.infinity_coder.chatiumapp.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestinationDsl
import androidx.navigation.compose.rememberNavController

@Composable
fun DialogScreen(
    navController: NavController,
    viewModel: DialogViewModel = viewModel(
        factory = DialogViewModelFactory(
            navController = navController
        )
    )
) {
    val messages by viewModel.messagesFlow.collectAsState(initial = emptyList())

    Column {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(messages) { data ->
                Text(text = data)
            }
        }
        SimpleFilledTextFieldSample(
            onSendText = {
                viewModel.onSendMessage(it)
            }
        )
    }
}

@Composable
fun SimpleFilledTextFieldSample(
    onSendText: (text: String) -> Unit
) {

    var text by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .weight(1f),
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") }
        )

        Button(
            modifier = Modifier
                .padding(ButtonDefaults.ContentPadding)
                .size(48.dp),
            shape = CircleShape,
            onClick = {
                onSendText(text)
                text = ""
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Send"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatPreview() {
    val navController = rememberNavController()
    DialogScreen(
        navController = navController,
        viewModel = DialogViewModel(navController)
    )
}