package ru.infinity_coder.chatiumapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import ru.infinity_coder.chatiumapp.presentation.authorization.phone_number.AuthPhoneNumberScreen
import ru.infinity_coder.chatiumapp.presentation.authorization.sms_code.AuthSmsCodeScreen
import ru.infinity_coder.chatiumapp.presentation.chats.ChatsScreen
import ru.infinity_coder.chatiumapp.presentation.dialog.DialogScreen


@Composable
fun ChatiumNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    isUserSignIn: Boolean
) {
    val startDestination = if (isUserSignIn) {
        ChatiumDestinations.MAIN_ROUTE
    } else {
        ChatiumDestinations.AUTH_ROUTE
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        mainGraph(navController)
        authGraph(navController)
    }
}

fun NavGraphBuilder.authGraph(
    navController: NavController
) {
    navigation(
        startDestination = ChatiumDestinations.PHONE_NUMBER_ROUTE,
        route = ChatiumDestinations.AUTH_ROUTE
    ) {
        composable(ChatiumDestinations.PHONE_NUMBER_ROUTE) { AuthPhoneNumberScreen(navController) }
        composable(ChatiumDestinations.SMS_CODE_ROUTE) { AuthSmsCodeScreen(navController) }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = ChatiumDestinations.CHATS_ROUTE,
        route = ChatiumDestinations.MAIN_ROUTE
    ) {
        composable(ChatiumDestinations.CHATS_ROUTE) { ChatsScreen(navController) }
        composable(ChatiumDestinations.DIALOG_ROUTE) { DialogScreen(navController) }
    }
}