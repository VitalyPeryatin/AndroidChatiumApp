package ru.infinity_coder.chatiumapp.presentation.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import ru.infinity_coder.chatiumapp.core.extensions.launch
import ru.infinity_coder.chatiumapp.data.ChatRepository

class DialogViewModel(
    navController: NavController
): ViewModel() {

    private val chatRepository = ChatRepository()

    val messagesFlow: Flow<List<String>> = chatRepository.provideMessagesFlow()

    fun onSendMessage(message: String) {
        launch {
            chatRepository.sendMessage(message)
        }
    }
}

class DialogViewModelFactory(
    private val navController: NavController
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DialogViewModel(navController) as T
    }
}