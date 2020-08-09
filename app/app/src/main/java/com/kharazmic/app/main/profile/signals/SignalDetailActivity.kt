package com.kharazmic.app.main.profile.signals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.kharazmic.app.R
import com.kharazmic.app.database.MyDatabase
import com.kharazmic.app.databinding.ActivitySignalDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignalDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignalDetailBinding
    private lateinit var database: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signal_detail)
        database = MyDatabase.getInstance(this)
        fetchDataFromDatabase()

        ExpansionLayoutCollection().apply {
            add(binding.stockLayout)
            add(binding.descriptionLayout)
            openOnlyOne(true)
        }




        binding.back.setOnClickListener {
            onBackPressed()
        }
    }


    private fun fetchDataFromDatabase() {
        intent.getStringExtra("category")?.let { category ->
            intent.getStringExtra("id")?.let { id ->
                database.signalDAO.getStockInfo(category = category, id = id).observe(this,
                    Observer { stock ->
                        binding.title.text = stock.title
                        binding.analyzer.text = stock.analyzer
                        binding.profitLimit.text = stock.profit
                        binding.date.text = stock.publishDate
                        binding.waitingTime.text = stock.waitingDate
                        binding.riskLimit.text = stock.loss
                        binding.group.text = stock.group
                        binding.description.text = stock.description
                    })
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.no_effect, R.anim.fade_out)
    }
}
