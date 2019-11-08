package com.mahmoudalyudeen.plutus.ui.bitcoin

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.mahmoudalyudeen.plutus.R
import java.text.SimpleDateFormat
import java.util.*

/** Custom tooltip displayed when a point on the chart is touched */
class BitcoinChartMarker(context: Context) : MarkerView(context, R.layout.marker_bitcoin) {

    private val dateTextView: TextView = findViewById(R.id.date_text_view)
    private val valueTextView: TextView = findViewById(R.id.value_text_view)

    private var format = SimpleDateFormat("dd/MM/YYYY", Locale.US)

    @SuppressLint("SetTextI18n") // irrelevant since app only displays US dollars
    override fun refreshContent(entry: Entry, highlight: Highlight?) {
        dateTextView.text = format.format(Date((entry.x * 1000).toLong()))
        valueTextView.text = "$${entry.y.toInt()}"
        super.refreshContent(entry, highlight)
    }

    override fun getOffset() = MPPointF((-(width / 2)).toFloat(), (-height - 50).toFloat())

}