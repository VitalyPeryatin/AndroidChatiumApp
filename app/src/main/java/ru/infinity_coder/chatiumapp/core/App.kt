package ru.infinity_coder.chatiumapp.core

import android.app.Application
import android.content.Context

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        WebSocketManager.startWebSocketListener()
    }

    companion object {
        lateinit var appContext: Context
    }
}