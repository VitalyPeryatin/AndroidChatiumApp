package ru.infinity_coder.chatiumapp.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import ru.infinity_coder.chatiumapp.core.App
import ru.infinity_coder.chatiumapp.core.wrappers.ActivityWrapper
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object AuthorizationRepository {

    private var smsCodeVerificationId = ""
    private var smsCodeToken: PhoneAuthProvider.ForceResendingToken? = null
    private val auth = Firebase.auth
    private val Context.authTokenPreferences: DataStore<Preferences> by preferencesDataStore(name = "authToken")
    private val tokenKey = stringPreferencesKey("token")

    suspend fun verifyVerificationCode(
        activityWrapper: ActivityWrapper,
        code: String
    ): Boolean {
        try {
            val credential = PhoneAuthProvider.getCredential(smsCodeVerificationId, code)
            val user = signInFirebase(activityWrapper, credential)
            val token = signInServer(user)
            saveToken(token)
        } catch (e: Exception) {
            Log.e("mLog", "verifyVerificationCode: " + e::class.java.simpleName)
            return false
        }
        return true
    }

    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null && hasValidToken()
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

    private suspend fun saveToken(token: String) {
        App.appContext.authTokenPreferences.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    private suspend fun getToken(): String? {
        return App.appContext.authTokenPreferences.data
            .map { it[tokenKey] }
            .firstOrNull()
    }

    private fun hasValidToken(): Boolean {
        return runBlocking { getToken() != null }
    }

    fun saveSmsCodeCredentials(
        verificationId: String,
        token: PhoneAuthProvider.ForceResendingToken
    ) {
        smsCodeVerificationId = verificationId
        smsCodeToken = token
    }
}