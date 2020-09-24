package com.kharazmic.app.main.home.desk.constant.detail

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.MPPointF
import com.kharazmic.app.Arrange
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
        binding.accounting.text = Arrange().persianConverter(info.accounting)
        binding.manager.text = info.manager
        binding.guarantor.text = Arrange().persianConverter(info.guarantor)
        binding.investmentManager.text = info.worth_amount
        binding.supervisor.text = info.supervisor
        binding.type.text = info.type
        binding.startDate.text = Arrange().persianConverter(info.start_date)
        binding.address.text = info.address
        binding.updateDate.text = Arrange().persianConverter(info.update_date)
        binding.pricePerUnit.text = Arrange().persianConverter(info.price_per_unit)
        binding.amountInvestUnit.text = Arrange().persianConverter(info.amount_invest_unit)
        binding.netWorth.text = Arrange().persianConverter(info.net_worth)
        binding.cancelPrice.text = Arrange().persianConverter(info.cancel_price)
        binding.numberOfInvestor2.text = Arrange().persianConverter(info.number_of_investor2)
        binding.percentageInvestor2.text = Arrange().persianConverter(info.percentage_investor2)
        binding.statisticPricePerUnit.text = Arrange().persianConverter(info.statistic_price_per_unit)
        binding.guaranteedProfit.text =Arrange().persianConverter(info.guaranteed_profit)
        binding.oneMonth.text = Arrange().persianConverter(info.one_month)
        binding.predicted.text = Arrange().persianConverter(info.predicted)
        binding.threeMonth.text = Arrange().persianConverter(info.three_month)
        binding.divide.text = Arrange().persianConverter(info.divide)
        binding.sixMonth.text = Arrange().persianConverter(info.six_month)
        binding.annual.text = Arrange().persianConverter(info.annual)
        binding.totalProfit.text = Arrange().persianConverter(info.total_profit)
        binding.numberInvestor1.text = Arrange().persianConverter(info.number_investor1)
        binding.percentageInvestor1.text = Arrange().persianConverter(info.percentage_investor1)


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
        values.add(PieEntry(info.stock.toFloat(), "سهام"))
        values.add(PieEntry(info.bank.toFloat(), "سپرده بانکی"))
        values.add(PieEntry(info.cash.toFloat(), "وجه نقد"))
        values.add(PieEntry(info.most_weight.toFloat(), "۵ سهم باارزش"))
        values.add(PieEntry(info.shared.toFloat(), " مشارکت"))
        values.add(PieEntry(info.other.toFloat(), "سایر "))



        val dataSet = PieDataSet(values, "").apply {
            this.sliceSpace = 2f
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
            this.xValuePosition=PieDataSet.ValuePosition.INSIDE_SLICE
            this.yValuePosition=PieDataSet.ValuePosition.OUTSIDE_SLICE

            this.valueLinePart1OffsetPercentage = 50f; /** When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size */
            this.valueLinePart1Length = 0.6f; /** When valuePosition is OutsideSlice, indicates length of first half of the line */
            this.valueLinePart2Length = 0.6f; /** When valuePosition is OutsideSlice, indicates length of second half of the line */
        }


        val data = PieData(dataSet).apply {
            this.setValueTextColor(Color.BLACK)
            this.setValueTextSize(12f)
        }
        binding.pieChart.apply{
            this.setUsePercentValues(true)
            setDrawEntryLabels(false)
            //legend.orientation = Legend.LegendOrientation.VERTICAL
            //legend.isEnabled = false
        }
        binding.pieChart.legend.apply {
            this.textSize = 12F
        }




        binding.pieChart.data = data
        binding.pieChart.description.text = ""
        binding.pieChart.isDrawHoleEnabled = false


    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}