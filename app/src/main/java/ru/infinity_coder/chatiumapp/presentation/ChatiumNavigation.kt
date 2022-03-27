package ru.infinity_coder.chatiumapp.presentation

import androidx.navigation.NavController

object ChatiumDestinations {
    const val AUTH_ROUTE = "auth"
    const val PHONE_NUMBER_ROUTE = "phoneNumber"
    const val SMS_CODE_ROUTE = "smsCode"

    const val MAIN_ROUTE = "main"
    const val DIALOG_ROUTE = "dialog"
    const val CHATS_ROUTE = "chats"
}

class ChatiumNavigationActions(navController: NavController) {

    val navigateToSmsCode: () -> Unit = {
        navController.navigate(ChatiumDestinations.SMS_CODE_ROUTE)
    }

    val navigateToMain: () -> Unit = {
        navController.navigate(ChatiumDestinations.MAIN_ROUTE)
    }
}