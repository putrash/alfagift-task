package com.putrash.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun String.capitalize(): String {
    return this.replaceFirstChar { it.uppercase() }
}

fun String.format(): String {
    return this.replace("\n", ". ");
}

fun <T> LiveData<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, onEventUnhandledContent: (T) -> Unit) {
    observe(lifecycleOwner, EventObserver {
        onEventUnhandledContent(it)
    })
}