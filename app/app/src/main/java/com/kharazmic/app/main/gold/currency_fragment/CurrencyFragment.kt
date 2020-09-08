package com.kharazmic.app.main.gold.currency_fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kharazmic.app.Address
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentCurrencyBinding
import com.kharazmic.app.main.gold.GoldCurrencyModel
import com.kharazmic.app.main.gold.GoldRecyclerAdapter


class CurrencyFragment : Fragment() {
    private val nimaArray = ArrayList<GoldCurrencyModel>()
    private val exchangeArray = ArrayList<GoldCurrencyModel>()
    private val sanaArray = ArrayList<GoldCurrencyModel>()

    private lateinit var binding: FragmentCurrencyBinding

    private val exchangeAdapter = GoldRecyclerAdapter()
    private val nimaAdapter = GoldRecyclerAdapter()
    private val sanaAdapter = GoldRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exchangeRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exchangeAdapter
        }
        binding.nimaRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = nimaAdapter
        }
        binding.sanaRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sanaAdapter
        }


        binding.moreExchange.setOnClickListener {
            val intent = Intent(activity, AllCurrencyActivity::class.java)
            intent.putExtra("info", exchangeArray)
            intent.putExtra("title", getString(R.string.exchange))
            activity?.startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        binding.moreSana.setOnClickListener {
            val intent = Intent(activity, AllCurrencyActivity::class.java)
            intent.putExtra("info", sanaArray)
            intent.putExtra("title", getString(R.string.sana))
            activity?.startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        binding.moreNima.setOnClickListener {
            val intent = Intent(activity, AllCurrencyActivity::class.java)
            intent.putExtra("info", nimaArray)
            intent.putExtra("title", getString(R.string.nima))
            activity?.startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        fetchData()

    }


    fun fetchData() {
        binding.loading.visibility = View.VISIBLE
        val request = object :
            JsonArrayRequest(Method.GET, Address().currency, null, Response.Listener {
                binding.loading.visibility = View.GONE

                nimaArray.clear()
                exchangeArray.clear()


                for (i in 0 until it.length()) {
                    val obj = it.getJSONObject(i)
                    when {
                        obj.getString("category") == "nima" -> nimaArray.add(
                            GoldCurrencyModel(
                                title = obj.getString("title"),
                                changePercentage = obj.getInt("percentage"),
                                price = obj.getString("price"),
                                date = obj.getString("date")
                            )
                        )
                        obj.getString("category") == "sana" -> sanaArray.add(
                            GoldCurrencyModel(
                                title = obj.getString("title"),
                                changePercentage = obj.getInt("percentage"),
                                price = obj.getString("price"),
                                date = obj.getString("date")
                            )
                        )
                        else -> exchangeArray.add(
                            GoldCurrencyModel(
                                title = obj.getString("title"),
                                changePercentage = obj.getInt("percentage"),
                                price = obj.getString("price"),
                                date = obj.getString("date")
                            )
                        )
                    }

                }


                try {
                    nimaAdapter.add(nimaArray.slice(0..4) as ArrayList<GoldCurrencyModel>)
                } catch (e: Exception) {
                    nimaAdapter.add(nimaArray)
                }

                try { sanaAdapter.add(sanaArray.slice(0..4) as ArrayList<GoldCurrencyModel>)
                } catch (e: Exception) { sanaAdapter.add(sanaArray) }

                try {
                    exchangeAdapter.add(exchangeArray.slice(0..4) as ArrayList<GoldCurrencyModel>)
                } catch (e: Exception) { exchangeAdapter.add(exchangeArray) }





                binding.moreExchange.visibility = View.VISIBLE
                binding.moreNima.visibility = View.VISIBLE
                binding.moreSana.visibility = View.VISIBLE

            }, Response.ErrorListener {
                try {
                    Log.i(
                        "Log",
                        "error in Currency Fragment ${String(
                            it.networkResponse.data,
                            Charsets.UTF_8
                        )}"
                    )
                } catch (e: Exception) {
                    Log.i("Log", "error in Currency Fragment $it")
                }

            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }
        request.retryPolicy = DefaultRetryPolicy(10000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        Volley.newRequestQueue(requireContext()).add(request)
    }

}