package com.kharazmic.app.main.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentCategoryBinding
import com.kharazmic.app.main.NewsTutorialClickListener


class CategoryFragment(private val api: String) : Fragment(), NewsTutorialClickListener {


    private lateinit var binding: FragmentCategoryBinding
    private val adapter = NewsRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.onClick = this
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)


        fetchNews()

        binding.refresh.setOnRefreshListener {
            fetchNews()
            binding.refresh.isRefreshing = false

        }
    }


    private fun fetchNews() {
        binding.loading.visibility = View.VISIBLE
        val request = JsonArrayRequest(
            Request.Method.GET,
            api,
            null,
            Response.Listener { response ->
                response?.let {
                    val data = ArrayList<NewsAdapterModel>()
                    for (i in 0 until it.length()) {
                        val obj = it.getJSONObject(i)
                        data.add(
                            NewsAdapterModel(
                                id = obj.getString("id"),
                                title = obj.getString("title"),
                                source = obj.getString("source"),
                                description = obj.getString("description"),
                                date = obj.getString("date"),
                                image = obj.getString("image")
                            )
                        )

                    }
                    adapter.add(data)
                    binding.loading.visibility = View.GONE
                }
            },
            Response.ErrorListener {

            })

        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }

    override fun onClick(id: String) {
        Log.i("Log", "  in ${activity.toString()} clicked on $id")
    }

}
