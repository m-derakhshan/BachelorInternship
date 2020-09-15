package com.kharazmic.app.main.home.desk.constant.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityConstantIncomeDetailBinding
import com.kharazmic.app.main.home.desk.constant.ConstantIncomeModel

class ConstantIncomeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConstantIncomeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val info = intent.getParcelableExtra("info") ?: ConstantIncomeModel()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_constant_income_detail)

        binding.title.text = info.name
        binding.name.text = info.name
        binding.accounting.text = info.accounting
        binding.manager.text = info.manager
        binding.guarantor.text = info.guarantor
        binding.worthAmount.text = info.worth_amount
        binding.supervisor.text = info.supervisor
        binding.type.text = info.type
        binding.startDate.text = info.start_date
        binding.address.text = info.address
        binding.updateDate.text = info.update_date
        binding.pricePerUnit.text = info.price_per_unit
        binding.amountInvestUnit.text = info.amount_invest_unit
        binding.netWorth.text = info.net_worth
        binding.cancelPrice.text = info.cancel_price
        binding.numberOfInvestor2.text = info.number_of_investor2
        binding.percentageInvestor2.text = info.percentage_investor2
        binding.statisticPricePerUnit.text = info.statistic_price_per_unit
        binding.stock.text = info.stock
        binding.bank.text = info.bank
        binding.cash.text = info.cash
        binding.other.text = info.other
        binding.shared.text = info.shared
        binding.mostWeight.text = info.most_weight
        binding.guaranteedProfit.text = info.guaranteed_profit
        binding.oneMonth.text = info.one_month
        binding.predicted.text = info.predicted
        binding.threeMonth.text = info.three_month
        binding.divide.text = info.divide
        binding.sixMonth.text = info.six_month
        binding.annual.text = info.annual
        binding.totalProfit.text = info.total_profit
        binding.numberInvestor1.text = info.number_investor1
        binding.percentageInvestor1.text = info.percentage_investor1


        binding.back.setOnClickListener {
            onBackPressed()
        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}