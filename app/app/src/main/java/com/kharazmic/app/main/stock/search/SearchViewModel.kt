package com.kharazmic.app.main.stock.search

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.database.model.SearchHistoryModel


class SearchViewModel(private val context: Context) : ViewModel() {


    var keyword = ""
    val searchResult = MutableLiveData<ArrayList<SearchHistoryModel>>()
    val loading = MutableLiveData<Int>()

    init {
        loading.value = View.GONE
    }


    fun searchFor(page: String) {
        loading.value = View.VISIBLE

        val request = JsonArrayRequest(
            Request.Method.POST,
            Address().stockSearch(keyword, page),
            null,
            Response.Listener {
                val result = ArrayList<SearchHistoryModel>()
                for (i in 0 until it.length()) {
                    val obj = it.getJSONObject(i)
                    result.add(
                        SearchHistoryModel(
                            id = obj.getString("id"),
                            name = obj.getString("name")
                        )
                    )
                }
                searchResult.value = result
                loading.value = View.GONE

            }, Response.ErrorListener {
                loading.value = View.GONE
                Log.i("Log", "error in search view model $it")

            })
        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }


}