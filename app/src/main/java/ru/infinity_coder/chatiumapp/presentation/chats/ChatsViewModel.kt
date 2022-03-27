package ru.infinity_coder.chatiumapp.presentation.chats

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

class ChatsViewModel(
    navController: NavController
): ViewModel() {

    var chats = mutableStateListOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8"
    )
        private set

}

class ChatsViewModelFactory(
    private val navController: NavController
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatsViewModel(navController) as T
    }
}