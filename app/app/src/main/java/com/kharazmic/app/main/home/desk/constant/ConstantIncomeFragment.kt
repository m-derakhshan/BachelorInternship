package com.kharazmic.app.main.home.desk.constant

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import com.kharazmic.app.main.home.desk.constant.detail.ConstantIncomeDetailActivity
import kotlinx.android.synthetic.main.cash_desk_filter_dialog.view.*


class ConstantIncomeFragment : Fragment(), ConstantIncomeRecyclerAdapter.ConstantIncomeListener {

    private lateinit var binding: FragmentConstantIncomeBinding
    private val myAdapter = ConstantIncomeRecyclerAdapter()
    private var riskCriteria: Int = 1
    private var averageProfit: Int = 0
    private var result = ArrayList<ConstantIncomeModel>()

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
        binding.filter.setOnClickListener {
            dialog()
        }


        var asc = true
        binding.riskFilter.setOnClickListener {
            if (asc) {
                if (riskCriteria == 0) {//alpha
                    val info = result.sortedBy { it.risk_criteria_alpha.toFloat() }
                    myAdapter.submitList(info)

                } else {//beta
                    val info = result.sortedBy { it.risk_criteria_beta.toFloat() }
                    myAdapter.submitList(info)
                }

            } else {
                if (riskCriteria == 0) {//alpha
                    val info = result.sortedByDescending { it.risk_criteria_alpha.toFloat() }
                    myAdapter.submitList(info)

                } else {//beta
                    val info = result.sortedByDescending { it.risk_criteria_beta.toFloat() }
                    myAdapter.submitList(info)
                }
            }

            asc = !asc
            binding.recyclerView.smoothScrollToPosition(0)
        }
    }


    private fun fetchData() {
        binding.loading.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.filter.visibility = View.GONE
        binding.title.visibility = View.GONE


        val request = object : JsonArrayRequest(
            Method.GET, Address().cashDesk("fixedincome"), null,
            Response.Listener
            {

                binding.loading.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.filter.visibility = View.VISIBLE
                binding.title.visibility = View.VISIBLE


                for (i in 0 until it.length()) {
                    Log.i("Log", "response is ${it.getJSONObject(i)}")
                    result.add(
                        Gson().fromJson(
                            it.getJSONObject(i).toString(),
                            ConstantIncomeModel::class.java
                        )
                    )
                }

                val data = ArrayList<ConstantIncomeModel>()
                data.addAll(result.sortedBy { info -> info.one_month })
                myAdapter.submitList(data)


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

    private fun dialog() {
        val view =
            LayoutInflater.from(requireContext())
                .inflate(
                    R.layout.cash_desk_filter_dialog, binding.title, false
                )
        val builder = AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(true)


        val filterDialog = builder.show()
        filterDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.risk.check(
            when (riskCriteria) {
                0 -> R.id.alpha
                else -> R.id.beta
            }
        )


        view.average.check(
            when (averageProfit) {
                0 -> R.id.monthly
                1 -> R.id.three_month
                2 -> R.id.six_month
                3 -> R.id.annual
                else -> R.id.from_beginning
            }
        )



        view.confirm.setOnClickListener {
            riskCriteria = when (view.risk.checkedRadioButtonId) {
                R.id.alpha -> 0
                else -> 1
            }

            averageProfit = when (view.average.checkedRadioButtonId) {
                R.id.monthly -> 0
                R.id.three_month -> 1
                R.id.six_month -> 2
                R.id.annual -> 3
                else -> 4
            }

            applyFilter()
            filterDialog.hide()

        }

    }

    private fun applyFilter() {
        when (riskCriteria) {
            0 -> {
                binding.riskFilter.text = "آلفا صندوق"
                myAdapter.riskCriteria = "alpha"
                myAdapter.notifyDataSetChanged()
            }
            else -> {
                binding.riskFilter.text = "بتا صندوق"
                myAdapter.riskCriteria = "beta"
                myAdapter.notifyDataSetChanged()
            }
        }


        when (averageProfit) {
            0 -> {
                binding.profitFilter.text = "بازدهی ماهانه"
                myAdapter.profitCriteria = 1
                myAdapter.notifyDataSetChanged()
            }
            1 -> {
                binding.profitFilter.text = "بازدهی سه ماهه"
                myAdapter.profitCriteria = 3
                myAdapter.notifyDataSetChanged()
            }
            2 -> {
                binding.profitFilter.text = "بازدهی شش ماهه"
                myAdapter.profitCriteria = 6
                myAdapter.notifyDataSetChanged()
            }
            3 -> {
                binding.profitFilter.text = "بازدهی سالانه"
                myAdapter.profitCriteria = 12
                myAdapter.notifyDataSetChanged()
            }
            else -> {
                binding.profitFilter.text = "بازدهی از آغاز فعالیت"
                myAdapter.profitCriteria = 0
                myAdapter.notifyDataSetChanged()
            }
        }

        myAdapter.notifyDataSetChanged()
    }


}