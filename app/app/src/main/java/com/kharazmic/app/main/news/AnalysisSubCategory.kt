package com.kharazmic.app.main.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kharazmic.app.R
import android.content.Intent
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.Utils
import com.kharazmic.app.databinding.FragmentAnalysisSubCategoryBinding
import com.kharazmic.app.main.NewsTutorialClickListener
import org.json.JSONArray
import org.json.JSONObject

class AnalysisSubCategory(private val category: String) : Fragment(), NewsTutorialClickListener {

    private var loadMore = false
    private lateinit var binding: FragmentAnalysisSubCategoryBinding
    private val adapter = NewsRecyclerViewAdapter()
    private var canLoadMore = true
    private var oldPage = 0


    var keyword = MutableLiveData<String>()
    private var page = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_analysis_sub_category,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.onClick = this

        val manager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter


        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = manager.childCount
                    val totalItemCount = manager.itemCount
                    val pastVisibleItems = manager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount && canLoadMore) {
                        binding.loading.visibility = View.VISIBLE

                        oldPage = page
                        page += 20

                        loadMore = true
                        fetchNews()
                    }
                }
            }
        }
        binding.recyclerView.addOnScrollListener(scrollListener)
        fetchNews()
        this.keyword.observe(viewLifecycleOwner, Observer {
            loadMore = false
            page = 0
            fetchNews()
        })

        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            reload()
        }
    }

    fun fetchNews() {
        val info = JSONObject()
        info.put("keyword", keyword.value)
        info.put("category", category)
        info.put("offset", page)
        info.put("limit", 20)
        val result = JSONArray()
        result.put(info)

        val api = Address().newsAPI()

        binding.loading.visibility = View.VISIBLE
        canLoadMore = false

        val request = object : JsonArrayRequest(
            Method.POST,
            api,
            result,
            Response.Listener { response ->
                response?.let {
                    val data = ArrayList<NewsAdapterModel>()
                    canLoadMore = false
                    for (i in 0 until it.length()) {
                        val tagsList = ArrayList<String>()
                        canLoadMore = true
                        val obj = it.getJSONObject(i)

                        obj.optJSONArray("tags")?.let { tags ->
                            for (tag in 0 until tags.length())
                                tagsList.add(tags.getString(tag))
                        }
                        data.add(
                            NewsAdapterModel(
                                id = obj.optString("id"),
                                title = obj.optString("title"),
                                source = obj.optString("source"),
                                description = obj.optString("description"),
                                date = obj.optString("date"),
                                image = "http://darakhshan.ir/profile.png",
                                tags = tagsList
                            )
                        )
                    }

                    if (loadMore)
                        adapter.addMore(data)
                    else
                        adapter.add(data)


                    binding.loading.visibility = View.GONE
                }
            },
            Response.ErrorListener {
                canLoadMore = true
                page = oldPage
                try {
                    Log.i(
                        "Log",
                        "Error in CategoryFragment ${
                            String(
                                it.networkResponse.data,
                                Charsets.UTF_8
                            )
                        }"
                    )
                } catch (e: Exception) {
                    Log.i("Log", "Error in CategoryFragment $it")
                }

            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = "Bearer ${Utils(context = context!!).token}"
                params["Accept"] = "Application/json"
                return params
            }

        }

        Volley.newRequestQueue(context).apply {
            add(request)
        }

    }

    private fun reload() {
        loadMore = false
        page = 0
        fetchNews()
    }

    override fun onClick(info: NewsAdapterModel) {
        val intent = Intent(activity, NewsDetailActivity::class.java)
        intent.putExtra("info", info)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }


}