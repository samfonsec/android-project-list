package de.mycraftnote.code_challenge.ui

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun String.onlyName() = split("-").takeIf { it.size > 1 }?.get(1)?.trim() ?: this

fun String.firstLetter() = onlyName().first().toString()

fun Long.toMilliseconds() = this * 1000

fun Long?.formatDate(locale: Locale): String {
    val time = this ?: System.currentTimeMillis()
    val formatter = SimpleDateFormat("EEE, MMM dd ''yy", locale)
    return formatter.format(time)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}