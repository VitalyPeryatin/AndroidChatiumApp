package ru.infinity_coder.chatiumapp.presentation.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ChatsScreen(
    navController: NavController,
    viewModel: ChatsViewModel = viewModel(
        factory = ChatsViewModelFactory(
            navController = navController
        )
    )
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Chatium")
                },
                actions = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                }
            )
        }
    ) {
        ChatsContent(viewModel.chats)
    }
}

@Composable
fun ChatsContent(chats: List<String>) {
    LazyColumn {
        items(chats) { chat ->
            ChatItem(chat)
        }
    }
}

@Composable
fun ChatItem(chat: String) {
    Row {
        Image(imageVector = Icons.Filled.Face, contentDescription = "Face")
        Column {
            Text(text = chat)
            Text(text = "2")
        }
    }
}

@Preview
@Composable
fun ChatsPreview() {
    val navController = rememberNavController()
    ChatsScreen(
        navController = navController,
        viewModel = ChatsViewModel(navController)
    )
}