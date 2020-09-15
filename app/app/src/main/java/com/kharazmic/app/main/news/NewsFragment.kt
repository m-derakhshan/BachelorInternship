package com.kharazmic.app.main.news


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentNewsBinding
import com.kharazmic.app.main.ViewPagerAdapter


class NewsFragment : Fragment(), SearchForHashTag {

    lateinit var binding: FragmentNewsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val stockFragment = CategoryFragment(parent = "news", category = "stock").apply { searchForHashTag = this@NewsFragment }
        val conclusionFragment = AnalysisCategoryFragment()
        val financialFragment = CategoryFragment(parent = "news", category = "financial").apply { searchForHashTag = this@NewsFragment }

        val adapter = ViewPagerAdapter(this).apply {
            this.addFragment(stockFragment)
            this.addFragment(financialFragment)
            this.addFragment(conclusionFragment)
        }
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 3


        val newsCategory = listOf(R.string.stock_market, R.string.financial, R.string.conclusion)
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
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        binding.search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                stockFragment.keyword.value = query
                financialFragment.keyword.value = query
                conclusionFragment.keyword.value = query

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                stockFragment.keyword.value = newText
                financialFragment.keyword.value = newText
                conclusionFragment.keyword.value = newText
                return false
            }

        })


        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            stockFragment.reload()
            financialFragment.reload()
            conclusionFragment.reload()

        }
    }

    override fun hashTag(tag: String) {
        binding.search.setQuery(tag, true)
        binding.search.isIconified = false
        val inputManager: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            binding.search.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )

    }

}
