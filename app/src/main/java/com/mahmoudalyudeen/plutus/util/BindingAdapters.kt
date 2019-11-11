package com.mahmoudalyudeen.plutus.util

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.mahmoudalyudeen.plutus.R
import com.mahmoudalyudeen.plutus.api.CallStatus
import com.mahmoudalyudeen.plutus.domain.BitcoinValue
import com.mahmoudalyudeen.plutus.ui.bitcoin.BitcoinChartMarker

@BindingAdapter("callStatusIdle")
fun bindIdleViewCallStatus(idleView: View, status: CallStatus?) {
    idleView.visibility = when (status) {
        CallStatus.Idle -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("callStatusLoading")
fun bindProgressViewCallStatus(loadingView: View, status: CallStatus?) {
    loadingView.visibility = when (status) {
        CallStatus.Loading -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("callStatusError")
fun bindErrorViewCallStatus(errorView: View, status: CallStatus?) {
    errorView.visibility = when (status) {
        CallStatus.Error -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("bitcoinValues")
fun bindLineChartBitcoinValues(lineChart: LineChart, bitcoinValue: List<BitcoinValue>?) {
    bitcoinValue?.let {
        val descriptionText = lineChart.context.getString(R.string.bitcoin_chart_description)
        val legendText = lineChart.context.getString(R.string.bitcoin_chart_legend)

        val accentColor = ContextCompat.getColor(lineChart.context, R.color.colorAccent)
        val transparent = ContextCompat.getColor(lineChart.context, R.color.colorTransparent)
        val primaryColor = ContextCompat.getColor(lineChart.context, R.color.colorTextPrimaryDark)
        val secondaryColor = ContextCompat.getColor(lineChart.context, R.color.colorTextSecondaryDark)

        val entries = bitcoinValue.map { Entry(it.timestamp.toFloat(), it.value.toFloat()) }
        val lineDataSet = LineDataSet(entries, legendText)

        lineDataSet.apply {
            setCircleColor(transparent)
            color = accentColor
            circleHoleColor = transparent
            setDrawHorizontalHighlightIndicator(false)
            setDrawVerticalHighlightIndicator(false)
        }

        lineChart.apply {
            data = LineData(lineDataSet)
            axisRight.setDrawLabels(false)
            axisRight.setDrawGridLines(false)
            xAxis.setDrawLabels(true)
            xAxis.labelCount = 12
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.labelRotationAngle = 270f
            xAxis.textColor = secondaryColor
            xAxis.valueFormatter = BitcoinTimeAxisFormatter()
            xAxis.setDrawGridLines(false)
            axisLeft.valueFormatter = BitcoinValueAxisFormatter()
            axisLeft.textColor = secondaryColor
            axisLeft.setLabelCount(10, true)
            description.text = descriptionText
            description.textColor = secondaryColor
            marker = BitcoinChartMarker(lineChart.context)
            legend.textColor = primaryColor
            setScaleEnabled(false)
            animateX( 3000)
            invalidate()
        }
    }
}
