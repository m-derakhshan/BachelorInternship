package com.kharazmic.app.main.desk.constant.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityConstantIncomeDetailBinding

class ConstantIncomeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConstantIncomeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_constant_income_detail)

        binding.back.setOnClickListener {
            onBackPressed()
        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}