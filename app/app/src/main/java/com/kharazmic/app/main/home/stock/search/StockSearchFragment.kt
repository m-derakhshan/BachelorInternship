package com.kharazmic.app.main.home.stock.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kharazmic.app.R
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.database.model.SearchHistoryModel
import com.kharazmic.app.databinding.FragmentStockSearchBinding
import kotlinx.coroutines.*


class StockSearchFragment : Fragment(), StockSearchClickListener {

    private var page = "1"
    lateinit var binding: FragmentStockSearchBinding
    private val scope = CoroutineScope(Dispatchers.Default)
    private lateinit var database: MyDatabase
    private lateinit var adapter: StockSearchRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_stock_search, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.searchEditText.requestFocus()

        database = MyDatabase.getInstance(context!!)
        val factory = SearchViewModelFactory(context = context!!)
        val viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        adapter = StockSearchRecyclerAdapter()
        adapter.onClick = this

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        database.searchHistoryDao.getAll().observe(viewLifecycleOwner, Observer { history ->
            history?.let {
                adapter.add(data = it, isHistory = true)
            }
        })
        viewModel.searchResult.observe(viewLifecycleOwner, Observer { search ->
            search?.let {
                adapter.add(data = it.toList(), isHistory = false)
            }

        })
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.keyword = s.toString()
                viewModel.searchFor(page = page)
            }

        })
        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)


        val imm: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.searchEditText, InputMethodManager.SHOW_IMPLICIT)


    }

    override fun onStockClick(stock: SearchHistoryModel) {
        scope.launch {
            async(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
                database.searchHistoryDao.add(stock)
            }).await()
            val info = Bundle()
            info.putString("id", stock.id)
            info.putString("name", stock.name)

            this@StockSearchFragment.findNavController()
                .navigate(R.id.action_stockSearchFragment_to_stockFragment, info)
        }


    }

    override fun onDeleteListener(id: Int, position: Int) {
        scope.launch {
            async(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
                database.searchHistoryDao.delete(id)
            }).await()
        }
        adapter.remove(position)
    }


}
