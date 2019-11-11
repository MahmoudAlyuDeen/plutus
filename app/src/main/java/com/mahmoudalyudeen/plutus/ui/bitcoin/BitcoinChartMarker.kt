package com.mahmoudalyudeen.plutus.ui.bitcoin

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.mahmoudalyudeen.plutus.R
import com.mahmoudalyudeen.plutus.util.dollarsWithSign
import com.mahmoudalyudeen.plutus.util.timestampToFullDate

/** Custom tooltip displayed when a point on the chart is touched */
class BitcoinChartMarker(context: Context) : MarkerView(context, R.layout.marker_bitcoin) {

    private val dateTextView: TextView = findViewById(R.id.date_text_view)
    private val valueTextView: TextView = findViewById(R.id.value_text_view)

    override fun refreshContent(entry: Entry, highlight: Highlight?) {
        dateTextView.text = entry.x.timestampToFullDate()
        valueTextView.text = entry.y.dollarsWithSign()
        super.refreshContent(entry, highlight)
    }

    override fun getOffset() = MPPointF((-(width / 2)).toFloat(), (-height - 50).toFloat())
}