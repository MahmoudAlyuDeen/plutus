package com.mahmoudalyudeen.plutus.util

import com.github.mikephil.charting.formatter.ValueFormatter

/** Formats the Y axis labels to display exchange rates */
class BitcoinValueAxisFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String = value.dollarsWithSign()
}

/** Formats the X axis labels to display month names */
class BitcoinTimeAxisFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String = value.timestampToShortDate()
}
