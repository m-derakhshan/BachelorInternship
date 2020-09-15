package com.kharazmic.app.main.home.digital_currency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
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

        binding.priceDollar.text = Arrange().numberEnglishArrangement(info.price_dollar)

        binding.changePriceIn24Hours.text =
            Arrange().concatenate(first = info.price_dollar_change_24.toString(), end = "%")
        binding.changePriceIn3Hours.text =
            Arrange().concatenate(first = info.price_dollar_change_3.toString(), end = "%")
        binding.changePriceIn7Days.text =
            Arrange().concatenate(first = info.price_dollar_change_7_days.toString(), end = "%")


        binding.valueOfTransaction24Hours.text =
            Arrange().numberEnglishArrangement(info.value_last_24_hours)
        binding.totalValue.text = Arrange().numberEnglishArrangement(info.total_value)
        binding.update.text = info.update
        binding.title.text = Arrange().concatenate(
            first = info.persian_name,
            end = Arrange().concatenate(first = " (", middle = info.english_name, end = ") ")
        )



        binding.changePriceIn7Days.setTextColor(
            ContextCompat.getColor(
                this,
                if (info.price_dollar_change_7_days > 0) R.color.dark_green else R.color.red
            )
        )

        binding.changePriceIn3Hours.setTextColor(
            ContextCompat.getColor(
                this,
                if (info.price_dollar_change_3 > 0) R.color.dark_green else R.color.red
            )
        )

        binding.changePriceIn24Hours.setTextColor(
            ContextCompat.getColor(
                this,
                if (info.price_dollar_change_24 > 0) R.color.dark_green else R.color.red
            )
        )


        binding.back.setOnClickListener {
            onBackPressed()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}