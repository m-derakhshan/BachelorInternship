package com.kharazmic.app.main.profile.signals


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.kharazmic.app.R
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.ActivitySignalDetailBinding


class SignalDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignalDetailBinding
    private lateinit var database: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signal_detail)
        database = MyDatabase.getInstance(this)
        fetchDataFromDatabase()
        ExpansionLayoutCollection().apply {
            add(binding.stockLayout)
            add(binding.descriptionLayout)
            openOnlyOne(true)
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }


        val lineEntry = ArrayList<Entry>()
        for (i in 2..10)
            lineEntry.add(Entry(i.toFloat(), i - 2))

        lineEntry.add(Entry(5F, 9))
        lineEntry.add(Entry(10F, 10))
        lineEntry.add(Entry(1F, 11))
        lineEntry.add(Entry(6F, 12))


        val lineLabels = ArrayList<String>()
        for (i in 0..13)
            lineLabels.add(i.toString())


        val lineDataSet = LineDataSet(lineEntry, "").apply {
            color = ContextCompat.getColor(baseContext, R.color.colorAccent)
            setDrawCircles(false)
            lineWidth = 3F
            setDrawValues(false)
        }


        val lineData = LineData(lineLabels, lineDataSet)
        binding.lineChart.data = lineData
        binding.lineChart.animateXY(2000, 2000)



        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.lineChart.axisRight.isEnabled = false


    }


    private fun fetchDataFromDatabase() {
        intent.getStringExtra("category")?.let { category ->
            intent.getStringExtra("id")?.let { id ->
                database.signalDAO.getStockInfo(category = category, id = id).observe(this,
                    Observer { stock ->
                        binding.title.text = stock.title
                        binding.analyzer.text = stock.analyzer
                        binding.profitLimit.text = stock.profit
                        binding.date.text = stock.publishDate
                        binding.waitingTime.text = stock.waitingDate
                        binding.riskLimit.text = stock.loss
                        binding.group.text = stock.group
                        binding.description.text = stock.description
                    })
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.no_effect, R.anim.fade_out)
    }
}
