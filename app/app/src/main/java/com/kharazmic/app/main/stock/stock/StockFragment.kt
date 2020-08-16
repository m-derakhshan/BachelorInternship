package com.kharazmic.app.main.stock.stock

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentStockBinding


class StockFragment : Fragment() {

    lateinit var binding: FragmentStockBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.title.text = arguments?.getString("name")
        val viewModel = ViewModelProvider(this).get(StockViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        val adapter = StockBuySellChartRecyclerViewAdapter()
        binding.chartRecyclerView.adapter = adapter
        binding.chartRecyclerView.layoutManager = LinearLayoutManager(context)

        val legalAdapter = StockLegalRealCharRecyclerViewAdapter()
        binding.legalChartRecyclerView.adapter = legalAdapter
        binding.legalChartRecyclerView.layoutManager = LinearLayoutManager(context)



        viewModel.fetchData()
        viewModel.chartBuySellInfo.observe(viewLifecycleOwner, Observer {
            adapter.add(it)
        })

        viewModel.chartLegalRealInfo.observe(viewLifecycleOwner, Observer { data ->

            legalAdapter.add(data)
        })

    }


}
