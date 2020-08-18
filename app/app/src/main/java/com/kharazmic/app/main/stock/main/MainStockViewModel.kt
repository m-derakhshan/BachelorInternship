package com.kharazmic.app.main.stock.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Arrange
import com.kharazmic.app.Utils
import com.kharazmic.app.database.model.BestSignalDAO
import com.kharazmic.app.database.model.BestStockModel
import kotlinx.coroutines.*

class MainStockViewModel(val context: Context, val database: BestSignalDAO) : ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    val isLoading = MutableLiveData<Boolean>()

    fun fetchData() {
        isLoading.value = true
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
                        }
                    }
                    isLoading.value = false
                },
                Response.ErrorListener {
                    isLoading.value = false
                    Log.i("Log", "error in MainStockViewModel $it")

                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = "Bearer ${Utils(context).token}"
                params["Accept"] = "Application/json"
                return params
            }
        }

        Volley.newRequestQueue(context).apply {
            add(request)
        }

    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}