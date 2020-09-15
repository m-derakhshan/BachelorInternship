package com.kharazmic.app.main.home.stock.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Utils
import com.kharazmic.app.database.model.BestSignalDAO
import com.kharazmic.app.database.model.BestStockModel
import kotlinx.coroutines.*

class MainStockViewModel(private val context: Context, private val database: BestSignalDAO) :
    ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)


    var isFetchedOnce = false


    fun fetchData() {
        if (isFetchedOnce)
            return
        val request = object :
            JsonObjectRequest(
                Method.GET,
                Address().bestStockAPI,
                null,
                Response.Listener { response ->
                    response?.let {
                        scope.launch {
                            async(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
                                val stocks = it.getJSONArray("stocks")
                                for (i in 0 until stocks.length()) {
                                    val obj = stocks.getJSONObject(i)
                                    database.add(
                                        BestStockModel(
                                            lastUpdate = it.getString("update"),
                                            name = obj.getString("name"),
                                            price = obj.getString("price"),
                                            full_name = obj.getString("full_name"),
                                            id = obj.getString("id"),
                                            profit = obj.getDouble("profit"),
                                            category = obj.getString("category")
                                        )
                                    )
                                }
                            }).await()
                            isFetchedOnce = true

                        }
                    }
                },
                Response.ErrorListener {

                    try {
                        Log.i(
                            "Log",
                            "error in MainStockViewModel ${String(
                                it.networkResponse.data,
                                Charsets.UTF_8
                            )}"
                        )

                    } catch (e: Exception) {
                        Log.i("Log", "error in MainStockViewModel $it")
                    }


                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = "Bearer ${Utils(context).token}"
                params["Accept"] = "Application/json"
                return params
            }
        }

        request.retryPolicy = DefaultRetryPolicy(7000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        Volley.newRequestQueue(context).apply {
            add(request)
        }

    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}