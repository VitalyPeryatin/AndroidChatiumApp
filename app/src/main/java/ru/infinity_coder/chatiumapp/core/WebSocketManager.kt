package ru.infinity_coder.chatiumapp.core

import android.util.Log
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.infinity_coder.chatiumapp.data.webSocketKtorHttpClient
import kotlin.coroutines.CoroutineContext

object WebSocketManager: CoroutineScope {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("mLog", throwable.message.toString())
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
    var session: DefaultClientWebSocketSession? = null
        private set

    private val _messagesFlow = MutableStateFlow<List<String>>(emptyList())
    val messagesFlow: Flow<List<String>> = _messagesFlow.asStateFlow()
    private var webSocketListenerJob: Job? = null

    fun startWebSocketListener() {
        webSocketListenerJob?.cancel()
        webSocketListenerJob = launch(Dispatchers.IO + coroutineExceptionHandler) {
            webSocketKtorHttpClient.ws(host = "192.168.178.70", port = 8080, path = "/chat") {
                session = this
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    _messagesFlow.emit(_messagesFlow.value + receivedText)
                }
            }
        }
    }

}