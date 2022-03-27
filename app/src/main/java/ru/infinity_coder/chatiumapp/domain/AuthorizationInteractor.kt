package ru.infinity_coder.chatiumapp.domain

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import ru.infinity_coder.chatiumapp.core.wrappers.ActivityWrapper
import ru.infinity_coder.chatiumapp.data.AuthorizationRepository

class AuthorizationInteractor {

    suspend fun onVerificationCompleted(
        activityWrapper: ActivityWrapper,
        credential: PhoneAuthCredential
    ): Boolean {
        val code = credential.smsCode
        if (code != null) {
            return trySignIn(activityWrapper, code)
        }

        return false
    }

    fun onVerificationFailed(exception: FirebaseException) {
        when (exception) {
            is FirebaseAuthInvalidCredentialsException -> {
                Log.e("mLog", "FirebaseAuthInvalidCredentialsException")
            }
            is FirebaseTooManyRequestsException -> {
                Log.e("mLog", "FirebaseTooManyRequestsException")
            }
            else -> {
                Log.e("mLog", "onVerificationFailed: " + exception::class.java.simpleName)
            }
        }
    }

    suspend fun trySignIn(
        activityWrapper: ActivityWrapper,
        code: String
    ): Boolean {
        return AuthorizationRepository.verifyVerificationCode(activityWrapper, code)
    }

    fun saveSmsCodeCredentials(
        verificationId: String,
        token: PhoneAuthProvider.ForceResendingToken
    ) {
        AuthorizationRepository.saveSmsCodeCredentials(verificationId, token)
    }

    fun isUserSignedIn(): Boolean {
        return AuthorizationRepository.isUserSignedIn()
    }
}