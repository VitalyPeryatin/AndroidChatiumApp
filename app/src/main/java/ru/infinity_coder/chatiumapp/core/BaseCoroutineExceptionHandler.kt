package ru.infinity_coder.chatiumapp.core

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

open class BaseCoroutineExceptionHandler: AbstractCoroutineContextElement(CoroutineExceptionHandler),
    CoroutineExceptionHandler {
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.d("ViewModel", exception.toString())
    }
}

@Suppress("FunctionName")
inline fun CustomCoroutineExceptionHandler(
    crossinline handler: (CoroutineContext, Throwable) -> Unit
): CoroutineExceptionHandler = object : BaseCoroutineExceptionHandler() {
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        super.handleException(context, exception)
        handler.invoke(context, exception)
    }
}