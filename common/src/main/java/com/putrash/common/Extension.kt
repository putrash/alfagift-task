package com.putrash.common

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import java.text.SimpleDateFormat
import java.util.*

fun String.capitalize(): String {
    return this.replaceFirstChar { it.uppercase() }
}

fun String.format(): String {
    return this.replace("\n", ". ")
}

fun Int.convertTime(): String {
    val hours = this / 60
    val minutes = this % 60
    return "${hours}h ${minutes}m"
}

fun String.convertDate(inputFormat: String, outputFormat: String): String {
    val formatter = SimpleDateFormat(inputFormat, Locale.getDefault())
    val formatParser = formatter.parse(this) ?: Date()
    val newOutputFormat = SimpleDateFormat(outputFormat, Locale.getDefault())

    return newOutputFormat.format(formatParser)
}

fun String?.handleAvatar(): String {
    return if (this?.contains("gravatar") == true) {
        this.drop(1) // Drop the first slash
    } else {
        BuildConfig.IMAGE_URL + this
    }
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