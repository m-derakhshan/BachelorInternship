package com.kharazmic.app.main.home.desk.constant.detail

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityConstantIncomeDetailBinding
import com.kharazmic.app.main.home.desk.constant.ConstantIncomeModel
import kotlinx.android.synthetic.main.activity_constant_income_detail.*

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


        ExpansionLayoutCollection().apply {
            this.add(binding.cashDeskInfoExpandLayout)
            this.add(binding.cashDeskProfitExpandLayout)
            this.add(binding.cashDeskInvestmentInfoExpandLayout)
            openOnlyOne(true)
        }


        binding.back.setOnClickListener {
            onBackPressed()
        }


        val values = ArrayList<PieEntry>()
        values.add(PieEntry(17F, "سهام"))
        values.add(PieEntry(20F, "سپرده بانکی"))
        values.add(PieEntry(20F, "وجه نقد"))
        values.add(PieEntry(10F, "۵ سهم باارزش"))
        values.add(PieEntry(15F, "اوراق مشارکت"))
        values.add(PieEntry(15F, "سایر دارایی ها"))



        val dataSet = PieDataSet(values, "").apply {
            this.sliceSpace = 1F
            colors = mutableListOf(
                ContextCompat.getColor(this@ConstantIncomeDetailActivity, R.color.dark_purple),
                ContextCompat.getColor(this@ConstantIncomeDetailActivity, R.color.colorAccent),
                ContextCompat.getColor(
                    this@ConstantIncomeDetailActivity,
                    R.color.semiPrimaryDarkColor
                ),
                ContextCompat.getColor(this@ConstantIncomeDetailActivity, R.color.colorPrimary),
                ContextCompat.getColor(this@ConstantIncomeDetailActivity, R.color.dark_pink),
                ContextCompat.getColor(this@ConstantIncomeDetailActivity, R.color.colorPrimaryDark)

            )
        }


        val data = PieData(dataSet).apply {
            this.setValueTextColor(Color.WHITE)
            this.setValueTextSize(12f)
        }

        binding.pieChart.legend.isEnabled = false


        binding.pieChart.data = data
        binding.pieChart.description.text = ""
        binding.pieChart.isDrawHoleEnabled = false


    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}