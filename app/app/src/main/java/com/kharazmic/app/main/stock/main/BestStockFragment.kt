package com.kharazmic.app.main.stock.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.R
import com.kharazmic.app.database.model.BestStockModel
import com.kharazmic.app.databinding.FragmentBestStockBinding

class BestStockFragment : Fragment(), BestStockRecyclerViewAdapter.BestStockListener {

    private var page = 1
    private var loadMore = false
    private lateinit var binding: FragmentBestStockBinding
    val myAdapter = BestStockRecyclerViewAdapter()
    private var category: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_best_stock, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        category = arguments?.getString("category")
        binding.title.text = category
        val manager = LinearLayoutManager(context)
        myAdapter.click = this
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = manager.childCount
                    val totalItemCount = manager.itemCount
                    val pastVisibleItems = manager.findFirstVisibleItemPosition()
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        page += 1
                        loadMore = true
                        fetchData()
                    }
                }
            }
        }

        binding.recyclerView.apply {
            this.adapter = myAdapter
            layoutManager = manager
            addOnScrollListener(scrollListener)
        }

        fetchData()


        binding.refresh.setOnRefreshListener {
            page = 1
            fetchData()
            binding.refresh.isRefreshing = false
        }

        binding.back.setOnClickListener {
            this.findNavController().navigateUp()
        }


    }


    private fun fetchData() {
        binding.loading.visibility = View.VISIBLE
        val request = object :
            JsonArrayRequest(
                Method.GET,
                Address().allBestStockAPI(page = page.toString(), category = category), null,
                Response.Listener {

                    val items = ArrayList<BestStockModel>()
                    for (i in 0 until it.length()) {
                        val obj = it.getJSONObject(i)
                        items.add(
                            BestStockModel(
                                id = obj.getString("id"),
                                category = obj.getString("category"),
                                name = obj.getString("name"),
                                profit = obj.getDouble("profit"),
                                full_name = obj.getString("full_name"),
                                price = obj.getString("price"),
                                lastUpdate = obj.optString("lastUpdate")
                            )
                        )
                    }

                    if (loadMore)
                        myAdapter.addMore(items)
                    else
                        myAdapter.add(items)
                    loadMore = false

                    binding.loading.visibility = View.GONE


                }, Response.ErrorListener {
                    Log.i("Log", "error in BestStockFragment $it")
                    binding.loading.visibility = View.GONE

                }
            ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }

        Volley.newRequestQueue(context).apply {
            add(request)
        }
    }

    override fun onClick(id: String, name: String) {

        val info = Bundle()
        info.putString("id", id)
        info.putString("name", name)

        this.findNavController().navigate(R.id.action_bestStockFragment_to_stockFragment, info)

    }

}
