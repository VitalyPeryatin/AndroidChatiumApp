package ru.infinity_coder.chatiumapp.data

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ru.infinity_coder.chatiumapp.core.wrappers.ActivityWrapper
import ru.infinity_coder.chatiumapp.data.preferences.AuthTokenPreferences
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object AuthorizationRepository {

    private var smsCodeVerificationId = ""
    private var smsCodeToken: PhoneAuthProvider.ForceResendingToken? = null
    private val auth = Firebase.auth
    private val authService = AuthService()

    suspend fun verifyVerificationCode(
        activityWrapper: ActivityWrapper,
        code: String
    ): Boolean {
        try {
            val credential = PhoneAuthProvider.getCredential(smsCodeVerificationId, code)
            val user = signInFirebase(activityWrapper, credential)
            val token = signInServer(user)
            val isLoginSuccessful = authService.login(token)
            if (isLoginSuccessful) {
                AuthTokenPreferences.saveToken(token)
            }
        } catch (e: Exception) {
            Log.e("mLog", "verifyVerificationCode: " + e::class.java.simpleName)
            return false
        }
        return true
    }

    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null && AuthTokenPreferences.getTokenSync() != null
    }

    private suspend fun signInFirebase(
        activityWrapper: ActivityWrapper,
        credential: PhoneAuthCredential
    ) : FirebaseUser {
        return suspendCoroutine { continuation ->
            auth.signInWithCredential(credential)
                .addOnCompleteListener(activityWrapper.activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("mLog", "signInWithCredential:success")
                        val user = task.result?.user
                        if (user != null) {
                            continuation.resume(user)
                        } else {
                            val throwable = IllegalStateException("User is null")
                            continuation.resumeWithException(throwable)
                        }
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w("mLog", "signInWithCredential:failure", task.exception)
                        /*if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Log.d("mLog", "The verification code entered was invalid")
                        }*/
                        val exception = task.exception ?: return@addOnCompleteListener
                        continuation.resumeWithException(exception)
                        // Update UI
                    }
                }
        }
    }

    private suspend fun signInServer(user: FirebaseUser): String {
        return suspendCoroutine { continuation ->
            user.getIdToken(false).addOnCompleteListener {
                val exception = it.exception
                if (exception != null) {
                    continuation.resumeWithException(exception)
                }

                continuation.resume(it.result.token.orEmpty())
            }
        }
    }

    fun saveSmsCodeCredentials(
        verificationId: String,
        token: PhoneAuthProvider.ForceResendingToken
    ) {
        smsCodeVerificationId = verificationId
        smsCodeToken = token
    }
}