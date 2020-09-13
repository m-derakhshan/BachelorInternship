package com.kharazmic.app.main.news

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayoutMediator
import com.kharazmic.app.Address
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.databinding.FragmentAnalysisCategoryBinding
import com.kharazmic.app.main.MainActivity
import com.kharazmic.app.main.NewsTutorialClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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