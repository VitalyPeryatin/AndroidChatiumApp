package ru.infinity_coder.chatiumapp.data

import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.Flow
import ru.infinity_coder.chatiumapp.core.WebSocketManager

class ChatRepository {

    private val session: DefaultClientWebSocketSession?
        get() = WebSocketManager.session

    suspend fun sendMessage(message: String) {
        session?.send(message)
    }

    fun provideMessagesFlow(): Flow<List<String>> {
        return WebSocketManager.messagesFlow
    }
}