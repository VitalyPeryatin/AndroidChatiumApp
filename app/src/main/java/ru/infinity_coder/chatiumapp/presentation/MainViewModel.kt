package ru.infinity_coder.chatiumapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.infinity_coder.chatiumapp.domain.AuthorizationInteractor

class MainViewModel: ViewModel() {

    private val authorizationInteractor = AuthorizationInteractor()

    val isUserSignIn by mutableStateOf(authorizationInteractor.isUserSignedIn())
}