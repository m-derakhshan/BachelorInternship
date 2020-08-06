package com.kharazmic.app.main.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.R
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.database.SignalDAO
import com.kharazmic.app.databinding.FragmentSignalsBinding
import kotlinx.coroutines.*


class SignalsFragment(private val category: String) : Fragment() {

    private lateinit var database: SignalDAO
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

        context?.let { database = MyDatabase.getInstance(it).signalDAO }


        val manager = LinearLayoutManager(context)

        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter

        database.getInfo(category).observe(viewLifecycleOwner, Observer {
            it?.let { data ->
                Log.i("Log","data is $it")
                adapter.addData(data = data)
            }
        })


        fetchData()
    }


    private fun fetchData() {

                val request = JsonArrayRequest(
                    Request.Method.GET,
                    Address().SignalsAPI(category = category, page = page),
                    null,
                    Response.Listener {
                        scope.launch {
                            async(
                                context = Dispatchers.Default,
                                start = CoroutineStart.DEFAULT,
                                block = {

                                    for (i in 0 until it.length()) {
                                        val obj = it.getJSONObject(i)
                                        database.add(
                                            SignalsModel(
                                                category = category,
                                                id =obj.getString("id") ,
                                                title = obj.getString("title"),
                                                group = obj.getString("title"),
                                                date = obj.getString("title"),
                                                description = obj.getString("title"),
                                                author = obj.getString("title"),
                                                loss = obj.getString("title"),
                                                profit = obj.getString("title"),
                                                realtimeProfit = obj.getString("title")
                                            )

                                        )
                                    }
                                }).await()
                        }

                    },
                    Response.ErrorListener {

                        Log.i("Log", "error in signals $it")
                    })

                val queue = Volley.newRequestQueue(context)
                queue.add(request)

    }


}
