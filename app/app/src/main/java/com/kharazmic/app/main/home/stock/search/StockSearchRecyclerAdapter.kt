package com.kharazmic.app.main.home.stock.search


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kharazmic.app.R
import com.kharazmic.app.database.model.SearchHistoryModel
import kotlinx.android.synthetic.main.search_history_item_model.view.*

class StockSearchRecyclerAdapter : RecyclerView.Adapter<StockSearchRecyclerAdapter.ViewHolder>() {

    private val items = ArrayList<SearchHistoryModel>()
    private var isHistory = false
    lateinit var onClick: StockSearchClickListener

    fun add(data: List<SearchHistoryModel>, isHistory: Boolean) {
        this.isHistory = isHistory
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        items.removeAt(position)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_history_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], position)


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: SearchHistoryModel, position: Int) {
            itemView.title.text = model.name
            itemView.delete.visibility =
                if (isHistory)
                    View.VISIBLE
                else View.GONE

            itemView.rootView.setOnClickListener {
                onClick.onStockClick(model)
            }
            itemView.delete.setOnClickListener {
                onClick.onDeleteListener(id = model.number, position = position)
            }

        }

    }

}


interface StockSearchClickListener {
    fun onStockClick(stock: SearchHistoryModel)

    fun onDeleteListener(id: Int, position: Int)

}