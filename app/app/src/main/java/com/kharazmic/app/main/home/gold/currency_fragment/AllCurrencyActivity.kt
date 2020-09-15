package com.kharazmic.app.main.home.gold.currency_fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityAllCurrencyBinding
import com.kharazmic.app.main.home.gold.GoldCurrencyModel
import com.kharazmic.app.main.home.gold.GoldRecyclerAdapter

class AllCurrencyActivity : AppCompatActivity() {

    private val currencyAdapter = GoldRecyclerAdapter()
    private lateinit var data: ArrayList<GoldCurrencyModel>
    private lateinit var binding: ActivityAllCurrencyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_currency)


        data = intent?.getParcelableArrayListExtra("info") ?: ArrayList()
        binding.title.text = intent.getStringExtra("title")

        binding.recyclerView.apply {
            adapter = currencyAdapter
            layoutManager = LinearLayoutManager(this@AllCurrencyActivity)
        }
        currencyAdapter.add(data)
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}