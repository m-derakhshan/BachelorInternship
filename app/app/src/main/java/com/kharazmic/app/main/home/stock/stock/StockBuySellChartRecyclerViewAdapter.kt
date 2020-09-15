package com.kharazmic.app.main.home.stock.stock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kharazmic.app.R
import kotlinx.android.synthetic.main.stock_chart_sell_buy_item_model.view.*

class StockBuySellChartRecyclerViewAdapter :
    RecyclerView.Adapter<StockBuySellChartRecyclerViewAdapter.ViewHolder>() {


    private val items = ArrayList<StockBuySellChartModel>()


    fun add(list:ArrayList<StockBuySellChartModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.stock_chart_sell_buy_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.binding(items[position])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun binding(model: StockBuySellChartModel) {
            itemView.sell_amount.text = model.sell_amount
            itemView.sell_volume.text = model.sell_volume
            itemView.sell_price.text = model.sell_price
            itemView.buy_price.text = model.buy_price
            itemView.buy_volume.text = model.buy_volume
            itemView.buy_amount.text = model.buy_amount
        }

    }
}