package com.kharazmic.app.main.gold

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import kotlinx.android.synthetic.main.gold_currency_recycler_item_model.view.*

class GoldRecyclerAdapter : RecyclerView.Adapter<GoldRecyclerAdapter.ViewHolder>() {

    private var items = ArrayList<GoldCurrencyModel>()

    fun add(list: ArrayList<GoldCurrencyModel>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gold_currency_recycler_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: GoldCurrencyModel) {

            itemView.percentage.text = Arrange().persianConcatenate(
                first = Arrange().persianConcatenate(
                    first = "(",
                    middle = model.changePercentage.toString(),
                    end = "%"
                ), end = ")"
            )



            itemView.percentage.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (model.changePercentage < 0) R.color.red
                    else R.color.light_green
                )
            )
            itemView.title.text = Arrange().persianConverter(model.title)
            itemView.date.text = Arrange().persianConverter(model.date)
            itemView.price.text = Arrange().persianConverter(model.price)
        }
    }
}