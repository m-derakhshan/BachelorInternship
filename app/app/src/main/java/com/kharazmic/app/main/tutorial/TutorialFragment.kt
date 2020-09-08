package com.kharazmic.app.main.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentTutorialBinding
import com.kharazmic.app.main.MainActivity
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


        val fundamentalFragment = CategoryFragment(parent = "tutorial", category = "fundamental")
        val technicalFragment = CategoryFragment(parent = "tutorial", category = "technical")
        val moneyFragment = CategoryFragment(parent = "tutorial", category = "money")
        val blockFragment = CategoryFragment(parent = "tutorial", category = "block")
        val goldFragment = CategoryFragment(parent = "tutorial", category = "gold")

        adapter.add(fundamentalFragment)
        adapter.add(technicalFragment)
        adapter.add(moneyFragment)
        adapter.add(blockFragment)
        adapter.add(goldFragment)


        val newsCategory = listOf(
            R.string.fundamental,
            R.string.technical,
            R.string.money_flow,
            R.string.block_chain,
            R.string.gold_currency
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
        binding.search.findViewById<TextView>(R.id.search_src_text)
            .setTextColor(ContextCompat.getColor(context!!, R.color.white))
        binding.search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                fundamentalFragment.keyword.value = query
                technicalFragment.keyword.value = query
                moneyFragment.keyword.value = query
                blockFragment.keyword.value = query
                goldFragment.keyword.value = query

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                fundamentalFragment.keyword.value = newText
                technicalFragment.keyword.value = newText
                moneyFragment.keyword.value = newText
                blockFragment.keyword.value = newText
                goldFragment.keyword.value = newText
                return false
            }
        })

    }

}
