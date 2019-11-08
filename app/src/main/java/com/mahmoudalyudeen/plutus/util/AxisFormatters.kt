package com.mahmoudalyudeen.plutus.util

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

/** Formats the Y axis labels to display exchange rates */
class BitcoinValueAxisFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String = "$${value.toInt()}"
}

/** Formats the X axis labels to display month names */
class BitcoinTimeAxisFormatter : ValueFormatter() {
    private var format = SimpleDateFormat("MMM. YY", Locale.US)

    override fun getFormattedValue(value: Float): String = format.format(Date((value * 1000).toLong()))
}
