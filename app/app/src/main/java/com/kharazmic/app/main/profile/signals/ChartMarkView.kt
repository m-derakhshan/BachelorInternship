package com.kharazmic.app.main.profile.signals

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.kharazmic.app.Arrange
import com.kharazmic.app.R


@SuppressLint("ViewConstructor")
class ChartMarkView(layout: Int, myContext: Context) : MarkerView(myContext, layout) {

    var price: TextView = findViewById(R.id.price_mark)
    private var date: TextView = findViewById(R.id.date_mark)
    private val chartDate = ArrayList<String>()

    fun addDate(date: ArrayList<String>) {
        chartDate.addAll(date)
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        price.text =
            Arrange().persianConcatenate(first = "قیمت: ", end = e?.`val`?.toInt().toString())
        date.text = Arrange().persianConcatenate(first = "تاریخ: ", end = chartDate[e?.xIndex!!])
    }

    override fun getYOffset(ypos: Float): Int = -(width * 0.65).toInt()

    override fun getXOffset(xpos: Float): Int = -height


}