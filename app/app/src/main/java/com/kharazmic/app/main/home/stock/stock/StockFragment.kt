package com.kharazmic.app.main.home.stock.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
