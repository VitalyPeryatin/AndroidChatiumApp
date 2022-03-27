package ru.infinity_coder.chatiumapp.data

import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders

class AuthService {

    suspend fun login(token: String): Boolean {
        val response = restKtorHttpClient.post<HttpResponse>(LOCAL_BASE_URL + "login") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        return response.status.value == 200
    }

}