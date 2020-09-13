package com.kharazmic.app.main.desk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityCashDeskBinding
import com.kharazmic.app.main.MainActivity
import com.kharazmic.app.main.desk.constant.ConstantIncomeFragment


class CashDeskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCashDeskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cash_desk)

        MainActivity.ViewPagerAdapter(this).apply {
            this.add(ConstantIncomeFragment())
            this.add(ConstantIncomeFragment())
            this.add(ConstantIncomeFragment())
            this.add(ConstantIncomeFragment())
            this.add(ConstantIncomeFragment())
        }.also { adapter ->
            binding.viewPager.adapter = adapter
        }


        binding.viewPager.offscreenPageLimit = 5

        val title = listOf(
            R.string.desk_cash_constant_income, R.string.desk_cash_blend,
            R.string.desk_cash_in_stock, R.string.desk_cash_special, R.string.desk_cash_etf
        )
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(title[position])
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()

        binding.back.setOnClickListener {
            onBackPressed()
        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}