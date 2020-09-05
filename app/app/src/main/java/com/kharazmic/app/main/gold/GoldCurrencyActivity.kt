package com.kharazmic.app.main.gold

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityGoldCurrencyBinding
import com.kharazmic.app.main.MainActivity
import com.kharazmic.app.main.gold.currency_fragment.CurrencyFragment
import com.kharazmic.app.main.gold.gold_fragment.GoldFragment

class GoldCurrencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGoldCurrencyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gold_currency)

        binding.back.setOnClickListener {
            onBackPressed()
        }


        val adapter = MainActivity.ViewPagerAdapter(this)
        adapter.add(GoldFragment())
        adapter.add(CurrencyFragment())
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 2
        val newsCategory = listOf(R.string.gold, R.string.currency)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(newsCategory[position])
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()


    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}
