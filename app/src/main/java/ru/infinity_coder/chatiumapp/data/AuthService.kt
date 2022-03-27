package ru.infinity_coder.chatiumapp.data

import io.ktor.client.request.get

class AuthService {

    suspend fun signIn() {
        val t: String = restKtorHttpClient.get(BASE_URL)
    }

}