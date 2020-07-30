package com.kharazmic.app.main.news


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kharazmic.app.Address
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentNewsBinding
import com.kharazmic.app.main.MainActivity


class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val adapter = MainActivity.ViewPagerAdapter(activity!!)
        binding.viewPager.adapter = adapter


        adapter.add(CategoryFragment(Address().NewsAPI("codal")))
        adapter.add(CategoryFragment(Address().NewsAPI("financial")))
        adapter.add(CategoryFragment(Address().NewsAPI("conclusion")))


        val newsCategory = listOf(R.string.codal, R.string.financial, R.string.conclusion)

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


    }
}
