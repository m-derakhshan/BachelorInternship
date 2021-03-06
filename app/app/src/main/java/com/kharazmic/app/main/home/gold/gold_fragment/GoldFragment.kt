package com.kharazmic.app.main.home.gold.gold_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.databinding.FragmentGoldBinding
import com.kharazmic.app.main.home.gold.GoldCurrencyModel
import com.kharazmic.app.main.home.gold.GoldRecyclerAdapter

class GoldFragment : Fragment() {

    private lateinit var binding: FragmentGoldBinding
    private val coinAdapter = GoldRecyclerAdapter()
    private val goldAdapter = GoldRecyclerAdapter()
    private val globalAdapter = GoldRecyclerAdapter()
    var requestNumber = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gold, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.coinRecycler.apply {
            adapter = coinAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.goldRecycler.apply {
            adapter = goldAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.globalRecycler.apply {
            adapter = globalAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }



        fetchData("gold")
        fetchData("coin")
        fetchData("globalgold")

    }


    fun fetchData(category: String) {
        binding.loading.visibility = View.VISIBLE
        val request = object :
            JsonArrayRequest(Method.GET, Address().goldCurrency(category), null,
                Response.Listener {
                    it?.let { result ->

                        requestNumber -= 1
                        if (requestNumber == 0)
                            binding.loading.visibility = View.GONE

                        val coinArray = ArrayList<GoldCurrencyModel>()
                        val goldArray = ArrayList<GoldCurrencyModel>()
                        val globalArray = ArrayList<GoldCurrencyModel>()

                        for (i in 0 until result.length()) {
                            val obj = result.getJSONObject(i)
                            when (obj.getString("category")) {
                                "coin" -> {
                                    coinArray.add(
                                        GoldCurrencyModel(
                                            title = obj.getString("title"),
                                            date = obj.getString("date"),
                                            price = obj.getString("price"),
                                            changePercentage = obj.getDouble("percentage")
                                        )
                                    )

                                }
                                "gold" -> {
                                    goldArray.add(
                                        GoldCurrencyModel(
                                            title = obj.getString("title"),
                                            date = obj.getString("date"),
                                            price = obj.getString("price"),
                                            changePercentage = obj.getDouble("percentage")
                                        )
                                    )
                                }
                                else -> {
                                    globalArray.add(
                                        GoldCurrencyModel(
                                            title = obj.getString("title"),
                                            date = obj.getString("date"),
                                            price = obj.getString("price"),
                                            changePercentage = obj.getDouble("percentage")
                                        )
                                    )
                                }
                            }
                        }


                        when (category) {
                            "gold" -> {
                                goldAdapter.add(goldArray)
                            }
                            "coin" -> {
                                coinAdapter.add(coinArray)
                            }
                            else -> {
                                globalAdapter.add(globalArray)
                            }
                        }


                    }

                }, Response.ErrorListener {
                    try {
                        Log.i(
                            "Log",
                            "error in GoldFragment ${
                                String(
                                    it.networkResponse.data,
                                    Charsets.UTF_8
                                )
                            }"
                        )
                    } catch (e: Exception) {
                        Log.i("Log", "error in GoldFragment $it")
                    }

                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = "Bearer ${Utils(context = requireContext()).token}"
                params["Accept"] = "Application/json"
                return params
            }
        }


        val queue = Volley.newRequestQueue(context)
        queue.add(request)

    }

}