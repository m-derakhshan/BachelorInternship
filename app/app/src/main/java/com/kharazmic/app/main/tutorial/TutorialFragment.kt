package com.kharazmic.app.main.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.kharazmic.app.Address

import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentTutorialBinding
import com.kharazmic.app.main.MainActivity
import com.kharazmic.app.main.NewsTutorialClickListener
import com.kharazmic.app.main.news.CategoryFragment


class TutorialFragment : Fragment() {


    private lateinit var binding: FragmentTutorialBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tutorial, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val adapter = MainActivity.ViewPagerAdapter(activity!!)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 5


        adapter.add(CategoryFragment(Address().TutorialAPI("fundamental")))
        adapter.add(CategoryFragment(Address().TutorialAPI("technical")))
        adapter.add(CategoryFragment(Address().TutorialAPI("money")))
        adapter.add(CategoryFragment(Address().TutorialAPI("block")))
        adapter.add(CategoryFragment(Address().TutorialAPI("gold")))


        val newsCategory = listOf(
            R.string.fundamental,
            R.string.technical,
            R.string.money_flow,
            R.string.block_chain,
            R.string.gold
        )

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(newsCategory[position])
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()


        binding.search.setOnSearchClickListener {
            binding.title.visibility = View.GONE
        }

        binding.search.setOnCloseListener {
            binding.title.visibility = View.VISIBLE
            false
        }

    }

}
