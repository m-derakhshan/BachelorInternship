package com.kharazmic.app.main.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AbsListViewBindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address

import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentCategoryBinding
import com.kharazmic.app.main.NewsTutorialClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CategoryFragment(private val parent: String, private val category: String) : Fragment(),
    NewsTutorialClickListener {

    private var loadMore = false
    private lateinit var binding: FragmentCategoryBinding
    private val adapter = NewsRecyclerViewAdapter()
    private val scope = CoroutineScope(Dispatchers.Main)


    var keyword = MutableLiveData<String>()
    private var page = 0


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
        val manager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter


        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = manager.childCount
                    val totalItemCount = manager.itemCount
                    val pastVisibleItems = manager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        binding.loading.visibility = View.VISIBLE
                        page += 1
                        loadMore = true
                        fetchNews()
                    }
                }
            }
        }

        binding.recyclerView.addOnScrollListener(scrollListener)


        fetchNews()

        binding.refresh.setOnRefreshListener {
            fetchNews()
            binding.refresh.isRefreshing = false

        }
        this.keyword.observe(viewLifecycleOwner, Observer {
            loadMore = false
            page = 0
            fetchNews()
        })

    }


    private fun fetchNews() {
        scope.launch {
            val api = if (parent == "news")
                Address().NewsAPI(keyword = keyword.value ?: "", category = category, page = page)
            else
                Address().TutorialAPI(
                    keyword = keyword.value ?: "",
                    category = category,
                    page = page
                )

            binding.refresh.isEnabled = true
            binding.loading.visibility = View.VISIBLE

            withContext(Dispatchers.Default) {
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

                            if (loadMore)
                                adapter.addMore(data)
                            else
                                adapter.add(data)
                            binding.loading.visibility = View.GONE
                            binding.refresh.isEnabled = false
                        }
                    },
                    Response.ErrorListener {
                        binding.refresh.isEnabled = true
                    })

                val queue = Volley.newRequestQueue(context)
                queue.add(request)
            }

        }
    }


    override fun onClick(id: String) {

    }

}
