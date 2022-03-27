package ru.infinity_coder.chatiumapp.presentation.authorization.sms_code

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.infinity_coder.chatiumapp.core.wrappers.ActivityWrapperImpl
import ru.infinity_coder.chatiumapp.domain.AuthorizationInteractor

class AuthSmsCodeViewModel(
    private val navController: NavController
): ViewModel() {

    private val authorizationInteractor = AuthorizationInteractor()

    fun onSmsCodeEntered(activity: Activity, code: String) {
        viewModelScope.launch {
            val isSignedIn = authorizationInteractor.trySignIn(
                activityWrapper = ActivityWrapperImpl(activity),
                code = code
            )
            if (isSignedIn) {
                navController.navigate("main")
            }
        }
    }
}

class AuthSmsCodeViewModelFactory(
    private val navController: NavController
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthSmsCodeViewModel(navController) as T
    }
}