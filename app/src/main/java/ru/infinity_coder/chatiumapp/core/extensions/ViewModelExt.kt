package ru.infinity_coder.chatiumapp.core.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.infinity_coder.chatiumapp.core.BaseCoroutineExceptionHandler

fun ViewModel.launch(
    exceptionHandler: CoroutineExceptionHandler = BaseCoroutineExceptionHandler(),
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(dispatcher + exceptionHandler, start, block)
}