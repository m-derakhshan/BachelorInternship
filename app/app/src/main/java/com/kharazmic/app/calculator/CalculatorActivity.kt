package com.kharazmic.app.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityCalculatorBinding
import org.json.JSONObject

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding
    //private lateinit var database: WageDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // database = DatabaseApp.getInstance(this).wageDAO
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculator)



        }





    private fun calculateStoke(
        amount: Int,
        buy_price: Int,
        sell_price: Int,
        wage: Float
    ): JSONObject {
        val result = JSONObject()
        result.put(
            "benefits_with_wage",
            (sell_price - buy_price) * amount * ((100 - wage) / 100F)
        )

        result.put(
            "benefits_without_wage",
            (sell_price - buy_price) * amount
        )


        return result
    }

}
