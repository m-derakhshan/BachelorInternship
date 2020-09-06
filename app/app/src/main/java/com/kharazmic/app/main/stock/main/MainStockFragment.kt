package com.kharazmic.app.main.stock.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.FragmentMainStockBinding


class MainStockFragment : Fragment(), BestStockRecyclerViewAdapter.BestStockListener {


    private lateinit var binding: FragmentMainStockBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_stock, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.search.setOnClickListener {
            this.findNavController().navigate(R.id.action_mainStockFragment_to_stockSearchFragment)
        }
        val database = MyDatabase.getInstance(requireContext()).bestStockDao
        val factory = MainStockViewModelFactory(requireContext(), database)
        val viewModel = ViewModelProvider(this, factory).get(MainStockViewModel::class.java)
        val technicalAdapter = BestStockRecyclerViewAdapter()
        val fundamentalAdapter = BestStockRecyclerViewAdapter()
        technicalAdapter.click = this
        fundamentalAdapter.click = this
        binding.calculator.setOnClickListener {
            this.findNavController().navigate(R.id.action_mainStockFragment_to_calculatorFragment)
        }
        binding.bestTechnicalRecyclerView.apply {
            adapter = technicalAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.bestFundamentalRecyclerView.apply {
            adapter = fundamentalAdapter
            layoutManager = LinearLayoutManager(context)
        }
        database.getStock("technical").observe(viewLifecycleOwner, Observer {
            binding.refresh.isRefreshing = false
            technicalAdapter.submitList(it.slice(0..2))
            binding.lastUpdateTechnical.text =
                Arrange().persianConcatenate(
                    end = it.firstOrNull()?.lastUpdate,
                    first = "بروزرسانی: "
                )
        })
        database.getStock("fundamental").observe(viewLifecycleOwner, Observer {
            fundamentalAdapter.submitList(it.slice(0..2))
            binding.lastUpdateFundamental.text =
                Arrange().persianConcatenate(
                    end = it.firstOrNull()?.lastUpdate,
                    first = "بروزرسانی: "
                )
        })
        viewModel.fetchData()
        binding.moreTechnical.setOnClickListener {
            val info = Bundle()
            info.putString("title", "بهترین های تکنیکال")
            info.putString("category", "technical")
            this.findNavController()
                .navigate(R.id.action_mainStockFragment_to_bestStockFragment, info)
        }
        binding.moreFundamental.setOnClickListener {
            val info = Bundle()
            info.putString("title", "بهترین های بنیادی")
            info.putString("category", "fundamental")
            this.findNavController()
                .navigate(R.id.action_mainStockFragment_to_bestStockFragment, info)
        }

        binding.refresh.setOnRefreshListener {
            viewModel.isFetchedOnce = false
            viewModel.fetchData()
        }
    }

    override fun onClick(id: String, name: String) {
        val info = Bundle()
        info.putString("id", id)
        info.putString("name", name)
        this.findNavController().navigate(R.id.action_mainStockFragment_to_stockFragment, info)

    }

}
