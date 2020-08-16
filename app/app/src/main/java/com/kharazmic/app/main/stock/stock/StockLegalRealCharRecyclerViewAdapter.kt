package com.kharazmic.app.main.stock.stock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kharazmic.app.R
import kotlinx.android.synthetic.main.legal_chart_item_model.view.*

class StockLegalRealCharRecyclerViewAdapter :
    RecyclerView.Adapter<StockLegalRealCharRecyclerViewAdapter.ViewHolder>() {

    private val items = ArrayList<StockLegalRealChartModel>()


    fun add(list: ArrayList<StockLegalRealChartModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.legal_chart_item_model, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(model: StockLegalRealChartModel) {
            itemView.legal_text.text = model.legal
            itemView.real_text.text = model.real
            itemView.subject.text = model.title
        }

    }

}