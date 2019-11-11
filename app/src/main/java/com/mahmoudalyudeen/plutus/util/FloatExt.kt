package com.mahmoudalyudeen.plutus.util

import java.text.SimpleDateFormat
import java.util.*

fun Float.dollarsWithSign(): String = "$${toInt()}"

fun Float.timestampToShortDate(): String {
    return SimpleDateFormat("MMM. YY", Locale.US).format(Date((this * 1000).toLong()))
}

fun Float.timestampToFullDate(): String {
    return SimpleDateFormat("dd/MM/YYYY", Locale.US).format(Date((this * 1000).toLong()))
}
