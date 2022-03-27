package ru.infinity_coder.chatiumapp.core

import android.app.Application
import android.content.Context

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        // TODO - удалить эту брешь в безопасности по мере готовности prod сервера
        HttpsTrustManager.allowAllSSL()
        appContext = applicationContext
        WebSocketManager.startWebSocketListener()
    }

    companion object {
        lateinit var appContext: Context
    }
}