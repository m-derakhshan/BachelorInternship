package com.kharazmic.app.main.stock.main

import android.os.Bundle
import android.util.Log
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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.search.setOnClickListener {
            this.findNavController().navigate(R.id.action_mainStockFragment_to_stockSearchFragment)
        }
        val database = MyDatabase.getInstance(context!!).bestStockDao
        val factory = MainStockViewModelFactory(context!!, database)
        val viewModel = ViewModelProvider(this, factory).get(MainStockViewModel::class.java)

        val technicalAdapter = BestStockRecyclerViewAdapter()
        val fundamentalAdapter = BestStockRecyclerViewAdapter()

        technicalAdapter.click = this
        fundamentalAdapter.click = this


        binding.bestTechnicalRecyclerView.apply {
            adapter = technicalAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.bestFundamentalRecyclerView.apply {
            adapter = fundamentalAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            it?.let { isLoading ->
                if (isLoading) {
                    binding.technicalLoading.visibility = View.VISIBLE
                    binding.bestTechnicalRecyclerView.visibility = View.INVISIBLE
                    binding.fundamentalLoading.visibility = View.VISIBLE
                    binding.bestFundamentalRecyclerView.visibility = View.INVISIBLE
                } else {
                    binding.technicalLoading.visibility = View.GONE
                    binding.bestTechnicalRecyclerView.visibility = View.VISIBLE
                    binding.fundamentalLoading.visibility = View.GONE
                    binding.bestFundamentalRecyclerView.visibility = View.VISIBLE
                }
            }
        })


        database.getStock("technical").observe(viewLifecycleOwner, Observer {
            Log.i("Log", "data fetched")
            technicalAdapter.add(it)
            binding.lastUpdateTechnical.text =
                Arrange().persianConcatenate(
                    end = it.firstOrNull()?.lastUpdate,
                    first = "بروزرسانی: "
                )
        })

        database.getStock("fundamental").observe(viewLifecycleOwner, Observer {
            fundamentalAdapter.add(it)
            binding.lastUpdateFundamental.text =
                Arrange().persianConcatenate(
                    end = it.firstOrNull()?.lastUpdate,
                    first = "بروزرسانی: "
                )
        })


        viewModel.fetchData()


        binding.moreTechnical.setOnClickListener {
            val info = Bundle()
            info.putString("category", "بهترین های تکنیکال")
            this.findNavController()
                .navigate(R.id.action_mainStockFragment_to_bestStockFragment, info)
        }
        binding.moreFundamental.setOnClickListener {
            val info = Bundle()
            info.putString("category", "بهترین های بنیادی")
            this.findNavController()
                .navigate(R.id.action_mainStockFragment_to_bestStockFragment, info)
        }

    }

    override fun onClick(id: String, name: String) {
        val info = Bundle()
        info.putString("id", id)
        info.putString("name", name)
        this.findNavController().navigate(R.id.action_mainStockFragment_to_stockFragment, info)

    }

}
