package com.kharazmic.app.main.home.stock.message

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Utils


class MessageViewModel(private val context: Context) : ViewModel() {

    var isFetched = false
    val isLoading = MutableLiveData<Boolean>()
    val messages = MutableLiveData<ArrayList<MessageModel>>()


    fun fetchMessage(page: Int) {
        if (isFetched)
            return
        isLoading.value = true
        val request = object :
            JsonArrayRequest(
                Method.GET,
                Address().supervisorMessage(page),
                null,
                Response.Listener { response ->
                    response?.let {
                        val result = ArrayList<MessageModel>()
                        for (i in 0 until it.length()) {
                            val obj = it.getJSONObject(i)
                            result.add(
                                MessageModel(
                                    id = obj.getString("id"),
                                    name = obj.getString("name"),
                                    date = obj.getString("date"),
                                    message = obj.getString("message"),
                                    symbol = obj.getString("symbol"),
                                    time = obj.getString("time")
                                )
                            )
                        }
                        isFetched = true
                        isLoading.value = false
                        messages.value = result
                    }
                },
                Response.ErrorListener
                {

                    try {
                        Log.i(
                            "Log",
                            "error in MessageViewModel ${String(
                                it.networkResponse.data,
                                Charsets.UTF_8
                            )}"
                        )

                    } catch (e: Exception) {
                        Log.i("Log", "error in MessageViewModel $it")
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
        Volley.newRequestQueue(context).apply { add(request) }

    }


}