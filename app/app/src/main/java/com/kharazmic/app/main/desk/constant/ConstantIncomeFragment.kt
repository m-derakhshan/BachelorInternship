package com.kharazmic.app.main.desk.constant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.kharazmic.app.Address
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentConstantIncomeBinding
import com.kharazmic.app.main.desk.constant.detail.ConstantIncomeDetailActivity
import id.zelory.compressor.overWrite
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class ConstantIncomeFragment : Fragment(), ConstantIncomeRecyclerAdapter.ConstantIncomeListener {

    private lateinit var binding: FragmentConstantIncomeBinding
    private val myAdapter = ConstantIncomeRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_constant_income, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            this.adapter = myAdapter
            this.layoutManager = LinearLayoutManager(context)
        }
        fetchData()
        myAdapter.listener = this

        val scroll = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                    binding.filter.hide()
                else
                    binding.filter.show()

                super.onScrolled(recyclerView, dx, dy)

            }
        }
        binding.recyclerView.addOnScrollListener(scroll)
    }


    private fun fetchData() {
        binding.loading.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.filter.visibility = View.GONE
        binding.title.visibility = View.GONE


        val request = object : JsonArrayRequest(
            Method.GET, Address().cashDesk("ConstantIncome"), null,
            Response.Listener
            {
                binding.loading.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.filter.visibility = View.VISIBLE
                binding.title.visibility = View.VISIBLE

                val result = ArrayList<ConstantIncomeModel>()
                for (i in 0 until it.length()) {
                    result.add(
                        Gson().fromJson(
                            it.getJSONObject(i).toString(),
                            ConstantIncomeModel::class.java
                        )
                    )
                }
                myAdapter.submitList(result)


            },
            Response.ErrorListener {
                try {

                    Log.i(
                        "Log",
                        "Error in ConstantIncomeFragment ${
                            String(
                                it.networkResponse.data,
                                Charsets.UTF_8
                            )
                        }"
                    )

                } catch (E: Exception) {
                    Log.i("Log", "Error in ConstantIncomeFragment $it")
                }


            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }
        Volley.newRequestQueue(requireContext()).add(request)
    }


    override fun onClick(model: ConstantIncomeModel) {
        val intent = Intent(activity, ConstantIncomeDetailActivity::class.java)
        intent.putExtra("info", model)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }


}