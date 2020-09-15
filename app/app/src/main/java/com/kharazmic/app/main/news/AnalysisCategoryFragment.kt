package com.kharazmic.app.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentAnalysisCategoryBinding
import com.kharazmic.app.main.MainActivity


class AnalysisCategoryFragment : Fragment(), SearchForHashTag {

    private lateinit var binding: FragmentAnalysisCategoryBinding
    var keyword = MutableLiveData<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_analysis_category, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val technicalFragment = CategoryFragment(parent = "news", category = "technical").apply {
            this.searchForHashTag = this@AnalysisCategoryFragment
        }
        val fundamentalFragment = CategoryFragment(parent = "news", category = "fundamental").apply {
                this.searchForHashTag = this@AnalysisCategoryFragment
            }

        val adapter = MainActivity.ViewPagerAdapter(requireActivity()).apply {
            this.add(technicalFragment)
            this.add(fundamentalFragment)
        }

        keyword.observe(viewLifecycleOwner, Observer {
            technicalFragment.keyword.value = it
            fundamentalFragment.keyword.value = it
        })

        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.isUserInputEnabled = true

        val stockCategory = listOf(R.string.technical, R.string.fundamental)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(stockCategory[position])
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }


    override fun hashTag(tag: String) {

    }


}