package com.kharazmic.app.main.home.stock

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kharazmic.app.R
import com.kharazmic.app.databinding.FragmentCalculatorBinding


class CalculatorFragment : Fragment() {

    private var totalTax: Float = 0.01039F
    private var buyTax: Float = 0.00464F
    private var sellTax: Float = 0.00575F
    private lateinit var binding: FragmentCalculatorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberOfStockToBePurchase.text = "0"
        binding.totalBuyPrice.text = "0"
        binding.totalSellPrice.text = "0"
        binding.buySellSubtraction.text = "0"
        binding.efficiencyPercentage.text = "0"

        binding.totalTax.text = totalTax.toString()
        binding.buyTax.setText(buyTax.toString())
        binding.sellTax.setText(sellTax.toString())



        binding.stockAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {

                    val totalStock = binding.stockAmount.text.toString().toInt()
                    val buyAmount = binding.buyPricePerStock.text.toString().toInt()
                    val sellAmount = binding.sellPricePerStock.text.toString().toInt()
                    val totalBuyPrice = (buyAmount * totalStock)
                    val totalSellPrice =
                        (sellAmount * totalStock) - (sellAmount * totalStock * sellTax)
                    val percentage = ((totalSellPrice / totalBuyPrice) * 100) - 100

                    binding.totalBuyPrice.text = String.format("%,d", totalBuyPrice)
                    binding.totalSellPrice.text = String.format("%,d", totalSellPrice.toInt())
                    binding.buySellSubtraction.text = String.format("%,d", (totalSellPrice - totalBuyPrice).toInt())
                    binding.efficiencyPercentage.text = String.format("%.3f", percentage)

                    binding.buySellSubtraction.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            if ((totalSellPrice - totalBuyPrice) > 0) R.color.green else R.color.red
                        )
                    )

                    binding.efficiencyPercentage.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            if (percentage > 0) R.color.green else R.color.red
                        )
                    )

                } catch (e: Exception) {

                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        binding.buyPricePerStock.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {

                    val totalStock = binding.stockAmount.text.toString().toInt()
                    val buyAmount = binding.buyPricePerStock.text.toString().toInt()
                    val sellAmount = binding.sellPricePerStock.text.toString().toInt()
                    val totalBuyPrice = (buyAmount * totalStock)
                    val totalSellPrice =
                        (sellAmount * totalStock) - (sellAmount * totalStock * sellTax)
                    val percentage = ((totalSellPrice / totalBuyPrice) * 100) - 100

                    binding.totalBuyPrice.text = String.format("%,d", totalBuyPrice)
                    binding.totalSellPrice.text = String.format("%,d", totalSellPrice.toInt())
                    binding.buySellSubtraction.text = String.format("%,d", (totalSellPrice - totalBuyPrice).toInt())
                    binding.efficiencyPercentage.text = String.format("%.3f", percentage)

                    binding.buySellSubtraction.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            if ((totalSellPrice - totalBuyPrice) > 0) R.color.green else R.color.red
                        )
                    )

                    binding.efficiencyPercentage.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            if (percentage > 0) R.color.green else R.color.red
                        )
                    )

                } catch (e: Exception) {

                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        binding.sellPricePerStock.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {

                    val totalStock = binding.stockAmount.text.toString().toInt()
                    val buyAmount = binding.buyPricePerStock.text.toString().toInt()
                    val sellAmount = binding.sellPricePerStock.text.toString().toInt()
                    val totalBuyPrice = (buyAmount * totalStock)
                    val totalSellPrice =
                        (sellAmount * totalStock) - (sellAmount * totalStock * sellTax)
                    val percentage = ((totalSellPrice / totalBuyPrice) * 100) - 100

                    binding.totalBuyPrice.text = String.format("%,d", totalBuyPrice)
                    binding.totalSellPrice.text = String.format("%,d", totalSellPrice.toInt())
                    binding.buySellSubtraction.text = String.format("%,d", (totalSellPrice - totalBuyPrice).toInt())
                    binding.efficiencyPercentage.text = String.format("%.3f", percentage)

                    binding.buySellSubtraction.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            if ((totalSellPrice - totalBuyPrice) > 0) R.color.green else R.color.red
                        )
                    )

                    binding.efficiencyPercentage.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            if (percentage > 0) R.color.green else R.color.red
                        )
                    )

                } catch (e: Exception) {

                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })



        binding.sellTax.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    sellTax = s.toString().toFloat()
                    totalTax = sellTax + buyTax
                    binding.totalTax.text = totalTax.toString()
                } catch (e: Exception) {

                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        binding.buyTax.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    buyTax = s.toString().toFloat()
                    totalTax = sellTax + buyTax
                    binding.totalTax.text = totalTax.toString()
                } catch (e: Exception) {

                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })



        binding.totalBuyPriceInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    val totalPrice = s.toString().toInt() - (s.toString().toInt()) * totalTax
                    val stockPrice = binding.pricePerStock.text.toString().toInt()
                    binding.numberOfStockToBePurchase.text =
                        (totalPrice / stockPrice).toInt().toString()
                } catch (e: Exception) {
                    binding.numberOfStockToBePurchase.text = "0"
                }

            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        binding.pricePerStock.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    val totalPrice =
                        binding.totalBuyPriceInput.text.toString().toInt() - (s.toString()
                            .toInt()) * totalTax
                    val stockPrice = s.toString().toInt()

                    binding.numberOfStockToBePurchase.text =
                        (totalPrice / stockPrice).toInt().toString()
                } catch (e: Exception) {
                    binding.numberOfStockToBePurchase.text = "0"
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })


        binding.back.setOnClickListener {
            this.findNavController().navigateUp()
        }
    }


}