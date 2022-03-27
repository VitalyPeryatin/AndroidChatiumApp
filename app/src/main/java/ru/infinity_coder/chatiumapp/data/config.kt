package ru.infinity_coder.chatiumapp.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

const val BASE_URL = "https://localhost/"

val webSocketKtorHttpClient = HttpClient(OkHttp) {

    installJsonFeature()
    installLogging()
    installResponseObserver()
    installDefaultRequest()

    install(WebSockets) {
    }
}

val restKtorHttpClient = HttpClient(Android) {
    installJsonFeature()
    installLogging()
    installResponseObserver()
    installDefaultRequest()
}

fun HttpClientConfig<out HttpClientEngineConfig>.installJsonFeature() {
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

fun HttpClientConfig<out HttpClientEngineConfig>.installLogging() {
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                android.util.Log.v("Logger Ktor =>", message)
            }
        }
        level = io.ktor.client.features.logging.LogLevel.ALL
    }
}

fun HttpClientConfig<out HttpClientEngineConfig>.installResponseObserver() {
    install(ResponseObserver) {
        onResponse { response ->
            Log.d("HTTP status:", "${response.status.value}")
        }
    }
}

fun HttpClientConfig<out HttpClientEngineConfig>.installDefaultRequest() {
    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}