package com.kharazmic.app.main.home.stock.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kharazmic.app.R
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.FragmentBestStockBinding

class BestStockFragment : Fragment(), BestStockRecyclerViewAdapter.BestStockListener {


    private lateinit var binding: FragmentBestStockBinding
    private val myAdapter = BestStockRecyclerViewAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_best_stock, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val database = MyDatabase.getInstance(requireContext()).bestStockDao
        arguments?.let { info ->
            binding.title.text = info.getString("title")
            info.getString("category")?.let {
                database.getStock(it).observe(viewLifecycleOwner, Observer { list ->
                    myAdapter.submitList(list)
                })
            }
        }
        val manager = LinearLayoutManager(context)
        myAdapter.click = this
        binding.recyclerView.apply {
            this.adapter = myAdapter
            layoutManager = manager
        }
        binding.back.setOnClickListener {
            this.findNavController().navigateUp()
        }
    }


    override fun onClick(id: String, name: String) {
        val info = Bundle()
        info.putString("id", id)
        info.putString("name", name)
        this.findNavController().navigate(R.id.action_bestStockFragment_to_stockFragment, info)

    }

}
