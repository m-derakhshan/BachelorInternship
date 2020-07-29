package com.kharazmic.app.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.kharazmic.app.R
import com.kharazmic.app.Utils
import com.kharazmic.app.databinding.ActivityMainBinding
import com.kharazmic.app.main.home.HomeFragment
import com.kharazmic.app.main.news.NewsFragment
import com.kharazmic.app.main.profile.ProfileFragment
import com.kharazmic.app.main.search.SearchFragment
import com.kharazmic.app.main.tutorial.TutorialFragment

class MainActivity : AppCompatActivity() {

    private lateinit var utils: Utils
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomMenu.selectedItemId = R.id.home
        utils = Utils(this)
        utils.isLoggedIn = true


        val adapter = ViewPagerAdapter(this)
        adapter.add(ProfileFragment())
        adapter.add(SearchFragment())
        adapter.add(HomeFragment())
        adapter.add(TutorialFragment())
        adapter.add(NewsFragment())
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false


        binding.viewPager.setCurrentItem(2, false)
        binding.viewPager.offscreenPageLimit = 5

        val scrollListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomMenu.selectedItemId = when (position) {
                    0 -> R.id.profile
                    1 -> R.id.search
                    2 -> R.id.home
                    3 -> R.id.tutorial
                    else -> R.id.news
                }

            }
        }
        binding.viewPager.registerOnPageChangeCallback(scrollListener)


        binding.bottomMenu.setOnNavigationItemSelectedListener { menu ->
            binding.viewPager.currentItem = when (menu.itemId) {
                R.id.profile -> 0
                R.id.search -> 1
                R.id.home -> 2
                R.id.tutorial -> 3
                else -> 4
            }
            true
        }


    }


    class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        private val items = ArrayList<Fragment>()

        fun add(fragment: Fragment) {
            items.add(fragment)
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = items.size

        override fun createFragment(position: Int): Fragment = items[position]


    }

}
