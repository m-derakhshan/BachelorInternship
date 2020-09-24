package com.kharazmic.app.main.home.desk.constant

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import kotlinx.android.synthetic.main.activity_constant_income_detail.*
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
                binding.riskIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
            } else {
                if (riskCriteria == 0) {//alpha
                    val info = result.sortedByDescending { it.risk_criteria_alpha.toFloat() }
                    myAdapter.submitList(info)

                } else {//beta
                    val info = result.sortedByDescending { it.risk_criteria_beta.toFloat() }
                    myAdapter.submitList(info)
                }
                binding.riskIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
            }

            asc = !asc
        }


        var ascProfit = true
        binding.profitFilter.setOnClickListener {

            if (ascProfit) {
                when (averageProfit) {
                    0 -> {
                        val info = result.sortedBy { it.one_month.toFloat() }
                        myAdapter.submitList(info)
                    }
                    1 -> {
                        val info = result.sortedBy { it.three_month.toFloat() }
                        myAdapter.submitList(info)
                    }
                    2 -> {
                        val info = result.sortedBy { it.six_month.toFloat() }
                        myAdapter.submitList(info)
                    }
                    3 -> {
                        val info = result.sortedBy { it.annual.toFloat() }
                        myAdapter.submitList(info)
                    }
                    else -> {
                        val info = result.sortedBy { it.total_profit.toFloat() }
                        myAdapter.submitList(info)
                    }


                }
                binding.profitIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
            }
            else {
                when (averageProfit) {
                    0 -> {
                        val info = result.sortedByDescending { it.one_month.toFloat() }
                        myAdapter.submitList(info)
                    }
                    1 -> {
                        val info = result.sortedByDescending { it.three_month.toFloat() }
                        myAdapter.submitList(info)
                    }
                    2 -> {
                        val info = result.sortedByDescending { it.six_month.toFloat() }
                        myAdapter.submitList(info)
                    }
                    3 -> {
                        val info = result.sortedByDescending { it.annual.toFloat() }
                        myAdapter.submitList(info)
                    }
                    else -> {
                        val info = result.sortedByDescending { it.total_profit.toFloat() }
                        myAdapter.submitList(info)
                    }


                }
                binding.profitIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
            }

            ascProfit = !ascProfit

        }
        myAdapter.registerAdapterDataObserver(object :RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                binding.recyclerView.smoothScrollToPosition(0)
            }
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                binding.recyclerView.smoothScrollToPosition(0)
            }
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                binding.recyclerView.smoothScrollToPosition(0)
            }
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.recyclerView.smoothScrollToPosition(0)
            }
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                binding.recyclerView.smoothScrollToPosition(0)
            }
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                binding.recyclerView.smoothScrollToPosition(0)
            }
        })

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
                myAdapter.submitList(result.sortedBy { this.riskCriteria })


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
                binding.riskFilterTxt.text = "آلفا صندوق"
                myAdapter.riskCriteria = "alpha"
                myAdapter.notifyDataSetChanged()
            }
            else -> {
                binding.riskFilterTxt.text = "بتا صندوق"
                myAdapter.riskCriteria = "beta"
                myAdapter.notifyDataSetChanged()
            }
        }


        when (averageProfit) {
            0 -> {
                binding.profitFilterTxt.text = "بازدهی ماهانه"
                myAdapter.profitCriteria = 1
                myAdapter.notifyDataSetChanged()
            }
            1 -> {
                binding.profitFilterTxt.text = "بازدهی۳ماهه"
                myAdapter.profitCriteria = 3
                myAdapter.notifyDataSetChanged()
            }
            2 -> {
                binding.profitFilterTxt.text = "بازدهی۶ماهه"
                myAdapter.profitCriteria = 6
                myAdapter.notifyDataSetChanged()
            }
            3 -> {
                binding.profitFilterTxt.text = "بازدهی سالانه"
                myAdapter.profitCriteria = 12
                myAdapter.notifyDataSetChanged()
            }
            else -> {
                binding.profitFilterTxt.text = "بازدهی کل"
                myAdapter.profitCriteria = 0
                myAdapter.notifyDataSetChanged()
            }
        }

        myAdapter.notifyDataSetChanged()
    }

}