package com.kharazmic.app.main.home.digital_currency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityDigitalCurrencyDetailBinding

class DigitalCurrencyDetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDigitalCurrencyDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_digital_currency_detail)

        val info = intent.getParcelableExtra("info") ?: DigitalCurrencyModel()

        binding.priceDollar.text = info.price_dollar
        binding.changePriceIn24Hours.text = Arrange().numberEnglishArrangement(info.price_dollar_change_24)
        binding.changePriceIn3Hours.text = info.price_dollar_change_3
        binding.changePriceIn7Days.text = info.price_dollar_change_7_days
        binding.valueOfTransaction24Hours.text = info.value_last_24_hours
        binding.totalValue.text = info.total_value
        binding.update.text = info.update
        binding.title.text = info.name



        binding.back.setOnClickListener {
            onBackPressed()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}