package ru.infinity_coder.chatiumapp.presentation.authorization.phone_number

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import ru.infinity_coder.chatiumapp.core.wrappers.ActivityWrapper
import ru.infinity_coder.chatiumapp.core.wrappers.ActivityWrapperImpl
import ru.infinity_coder.chatiumapp.domain.AuthorizationInteractor
import java.util.concurrent.TimeUnit

class AuthPhoneNumberViewModel(
    private val navController: NavController
): ViewModel() {

    private val authorizationInteractor = AuthorizationInteractor()
    // TODO Говнокод
    private var activityWrapper: ActivityWrapper? = null

    init {
        viewModelScope.launch {
            if (authorizationInteractor.isUserSignedIn()) {
                navController.navigate("main")
            }
        }
    }

    private val otpSmsCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            val activityWrapper = activityWrapper ?: return
            viewModelScope.launch {
                authorizationInteractor.onVerificationCompleted(activityWrapper, credential)
            }
        }

        override fun onVerificationFailed(exception: FirebaseException) {
            authorizationInteractor.onVerificationFailed(exception)
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(verificationId, token)
            authorizationInteractor.saveSmsCodeCredentials(verificationId, token)
            navController.navigate("smsCode")
        }
    }

    fun onSignInClicked(
        activity: Activity,
        phoneNumber: String
    ) {
        sendOtpSms(activity, phoneNumber)
    }

    private fun sendOtpSms(
        activity: Activity,
        phoneNumber: String
    ) {
        activityWrapper = ActivityWrapperImpl(activity)
        val options = PhoneAuthOptions.newBuilder()
            .setPhoneNumber(phoneNumber)
            .setTimeout(10L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(otpSmsCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}

class AuthPhoneNumberViewModelFactory(
    private val navController: NavController
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthPhoneNumberViewModel(navController) as T
    }
}