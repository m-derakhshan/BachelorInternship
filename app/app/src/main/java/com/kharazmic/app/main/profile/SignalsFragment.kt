package com.kharazmic.app.main.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentSignalsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignalsFragment(private val category: String) : Fragment() {

    private val page = 0
    private val adapter = SignalRecyclerViewAdapter()
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var binding: FragmentSignalsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signals, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val manager = LinearLayoutManager(context)

        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter

        fetchData()
    }


    private fun fetchData() {
        scope.launch {
            withContext(Dispatchers.Default) {

                val data = ArrayList<SignalsModel>()


                val request = JsonArrayRequest(
                    Request.Method.GET,
                    Address().SignalsAPI(category = category, page = page),
                    null,
                    Response.Listener {
                        Log.i("Log"," response is  $it")
                        for (i in 0 until it.length()) {
                            val obj = it.getJSONObject(i)
                            data.add(
                                SignalsModel(
                                    title = obj.getString("title"),
                                    group = obj.getString("title"),
                                    date = obj.getString("title"),
                                    description = obj.getString("title"),
                                    author = obj.getString("title"),
                                    loss = obj.getString("title"),
                                    profit = obj.getString("title"),
                                    realtimeProfit =obj.getString("title"))
                                )
                        }
                        adapter.addData(data)


                    },
                    Response.ErrorListener {

                        Log.i("Log","error in signals $it")
                    })

                val queue = Volley.newRequestQueue(context)
                queue.add(request)
            }
        }
    }


}
