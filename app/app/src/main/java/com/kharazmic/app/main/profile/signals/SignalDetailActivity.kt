package com.kharazmic.app.main.profile.signals


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.kharazmic.app.Address
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.ActivitySignalDetailBinding
import kotlinx.coroutines.*
import org.json.JSONObject


class SignalDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignalDetailBinding
    private lateinit var database: MyDatabase
    private lateinit var id: String
    lateinit var markView: ChartMarkView

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signal_detail)
        database = MyDatabase.getInstance(this)
        fetchDataFromDatabase()
        fetchDataFromServer()
        ExpansionLayoutCollection().apply {
            add(binding.stockLayout)
            add(binding.descriptionLayout)
            openOnlyOne(true)
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.refresh.setOnRefreshListener {
            fetchDataFromServer()
        }
        binding.lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            gridColor = ContextCompat.getColor(baseContext, R.color.light_gray)
        }
        binding.lineChart.axisRight.apply {
            gridColor = ContextCompat.getColor(baseContext, R.color.light_gray)
        }
        binding.lineChart.axisLeft.isEnabled = false
        binding.lineChart.setDescription("")
        binding.lineChart.legend.isEnabled = false
        markView = ChartMarkView(R.layout.chart_mark_view, this)
        binding.lineChart.markerView = markView


    }


    private fun fetchDataFromDatabase() {
        intent.getStringExtra("category")?.let { category ->
            intent.getStringExtra("id")?.let { id ->
                this.id = id
                database.signalDAO.getStockInfo(category = category, id = id).observe(this,
                    Observer { stock ->
                        val profit = stock.realTimeProfit

                        binding.realTimeProfit.text = Arrange().persianConcatenate(
                            first = "این سهم تا این لحظه ",
                            middle = stock.realTimeProfit,
                            end = " درصد بازدهی داشته است."
                        )

                        val percentage = Arrange().persianConverter(profit) + "%"
                        binding.percentage.text = percentage

                        binding.profit.progress = profit.toFloat()
                        if (profit.toInt() > 0)
                            binding.profit.progressBarColor =
                                ContextCompat.getColor(this@SignalDetailActivity, R.color.green)
                        else
                            binding.profit.progressBarColor =
                                ContextCompat.getColor(this@SignalDetailActivity, R.color.red)


                        binding.title.text = stock.title
                        binding.analyzer.text = stock.analyzer
                        binding.profitLimit.text = Arrange().persianConverter(stock.profit)
                        binding.date.text = Arrange().persianConverter(stock.publishDate)
                        binding.waitingTime.text = Arrange().persianConcatenate(
                            first = stock.waitingDate, end = "روز"
                        )
                        binding.riskLimit.text = Arrange().persianConverter(stock.loss)
                        binding.group.text = stock.group
                        binding.description.text = stock.description
                    })
            }
        }
    }

    private fun fetchDataFromServer() {
        binding.refresh.isRefreshing = true
        val info = JSONObject()
        info.put("id", id)
        val request = JsonObjectRequest(
            Request.Method.POST,
            Address().signalDetailAPI,
            info,
            Response.Listener {
                scope.launch {

                    withContext(Dispatchers.Main, block = {
                        it?.let { data ->

                            async(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
                                database.signalDAO.addProfit(
                                    id = id,
                                    profit = data.getString("profit")
                                )

                            }).await()


                            val lineEntry = ArrayList<Entry>()
                            val lineLabels = ArrayList<String>()

                            data.getJSONArray("chart").let { chart ->
                                for (i in 0 until chart.length()) {
                                    val point = chart.getJSONObject(i)
                                    lineEntry.add(Entry(point.getInt("y").toFloat(), i))
                                    lineLabels.add(Arrange().persianConverter(point.getString("x")))
                                }
                                val lineDataSet = LineDataSet(lineEntry, "").apply {
                                    color = ContextCompat.getColor(baseContext, R.color.colorAccent)
                                    setDrawCircles(false)
                                    lineWidth = 2F
                                    setDrawValues(false)
                                }
                                val lineData = LineData(lineLabels, lineDataSet)

                                binding.lineChart.data = lineData
                                binding.lineChart.animateXY(2000, 2000)

                                markView.addDate(lineLabels)
                                binding.refresh.isRefreshing = false
                            }
                        }
                    })
                }
            },
            Response.ErrorListener {
                Utils(this).showSnackBar(
                    color = ContextCompat.getColor(this, R.color.black),
                    msg = "بروز رسانی اطلاعات با خطا مواجه شد.",
                    snackView = binding.root
                )
                binding.refresh.isRefreshing = false
                Log.i("Log", "Error in SignalDetailActivity $it")

            })
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.no_effect, R.anim.fade_out)
    }
}
