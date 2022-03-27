package ru.infinity_coder.chatiumapp.presentation.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun Context.getActivity(): Activity? {
  var currentContext = this
  while (currentContext is ContextWrapper) {
       if (currentContext is Activity) {
            return currentContext
       }
       currentContext = currentContext.baseContext
  }
  return null
}

fun Context.requireActivity(): Activity {
    return getActivity() ?: throw IllegalStateException("No Activity instance for this context")
}