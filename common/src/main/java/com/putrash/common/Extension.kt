package com.putrash.common

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
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

inline fun <reified T> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}