package com.kharazmic.app.main.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentCategoryBinding
import com.kharazmic.app.main.news.NewsRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class TutorialCategory : Fragment() {

    private var loadMore = false
    private lateinit var binding: FragmentCategoryBinding
    private val adapter = NewsRecyclerViewAdapter()
    private val scope = CoroutineScope(Dispatchers.Main)

    var keyword = MutableLiveData<String>()
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tutorial_category, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter
    }






}