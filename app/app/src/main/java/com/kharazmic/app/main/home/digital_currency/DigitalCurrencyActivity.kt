package com.kharazmic.app.main.home.digital_currency

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityDigitalCurrencyBinding

class DigitalCurrencyActivity : AppCompatActivity(),
    DigitalCurrencyRecyclerAdapter.DigitalCurrencyListener {

    private lateinit var binding: ActivityDigitalCurrencyBinding
    private val myAdapter = DigitalCurrencyRecyclerAdapter().apply {
        this.clickListener = this@DigitalCurrencyActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_digital_currency)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.recyclerView.apply {
            this.adapter = myAdapter
            this.layoutManager = LinearLayoutManager(this@DigitalCurrencyActivity)
        }

        fetchData()

        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            fetchData()
        }

    }


    private fun fetchData() {
        binding.loading.visibility = View.VISIBLE
        val request = object : JsonArrayRequest(Method.GET, Address().digitalCurrency, null,
            Response.Listener {
                val resultList = ArrayList<DigitalCurrencyModel>()
                for (i in 0 until it.length()) {
                    val obj = it.getJSONObject(i)
                    resultList.add(
                        DigitalCurrencyModel(
                            id = obj.optString("id"),
                            persian_name = obj.optString("persian_name"),
                            english_name = obj.optString("english_name"),
                            icon = obj.optString("icon"),
                            price_dollar = obj.optString("price_dollar"),
                            price_dollar_change_3 = obj.optDouble("price_dollar_change_3"),
                            price_dollar_change_7_days = obj.optDouble("price_dollar_change_7_days"),
                            price_dollar_change_24 = obj.optDouble("price_dollar_change_24"),
                            price_rial = obj.optString("price_rial"),
                            price_rial_change_24 = obj.optDouble("price_rial_change_24"),
                            total_value = obj.optString("total_value"),
                            update = obj.optString("update"),
                            value_last_24_hours = obj.optString("value_last_24_hours")
                        )
                    )


                    myAdapter.submitList(resultList)

                }

                binding.loading.visibility = View.GONE
            }, Response.ErrorListener {

                try {
                    Log.i(
                        "Log",
                        "Error in DigitalCurrencyActivity ${
                            String(
                                it.networkResponse.data,
                                Charsets.UTF_8
                            )
                        }"
                    )

                } catch (e: Exception) {
                    Log.i("Log", "Error in DigitalCurrencyActivity $it")
                }

            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }

        Volley.newRequestQueue(this).add(request)
    }


    override fun onClick(model: DigitalCurrencyModel) {
        val intent = Intent(this, DigitalCurrencyDetailActivity::class.java)
        intent.putExtra("info", model)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}