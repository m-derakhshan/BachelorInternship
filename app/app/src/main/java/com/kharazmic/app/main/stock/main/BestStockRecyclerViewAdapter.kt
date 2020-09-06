package com.kharazmic.app.main.stock.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import com.kharazmic.app.database.model.BestStockModel
import kotlinx.android.synthetic.main.stock_best_stock_item_model.view.*


class BestStockRecyclerViewAdapter :
    ListAdapter<BestStockModel, BestStockRecyclerViewAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<BestStockModel>() {
        override fun areItemsTheSame(oldItem: BestStockModel, newItem: BestStockModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: BestStockModel, newItem: BestStockModel): Boolean =
            oldItem == newItem

    }) {


    lateinit var click: BestStockListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stock_best_stock_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
        {
            holder.bind(getItem(position))
            holder.itemView.rootView.setOnClickListener {
                click.onClick(id = getItem(position).id,name = getItem(position).name)
            }

        }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: BestStockModel) {
            itemView.title.text = model.name
            itemView.price.text = Arrange().persianConcatenate(first = model.price, end = " ریال ")
            itemView.full_name.text = model.full_name
            itemView.percentage.text =

                Arrange().persianConcatenate(
                    first = Arrange().persianConcatenate(
                        first = "(",
                        middle = model.profit.toString(),
                        end = "%"
                    ), end = ")"
                )

            itemView.percentage.setTextColor(
                if (model.profit > 0)
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.dark_green
                    )
                else
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.red
                    )
            )
        }
    }

    interface BestStockListener {
        fun onClick(id: String, name: String)
    }
}


//
//
//class BestStockRecyclerViewAdapter :
//    RecyclerView.Adapter<BestStockRecyclerViewAdapter.ViewHolder>() {
//
//
//    private var items = ArrayList<BestStockModel>()
//    lateinit var click: BestStockListener
//
//    fun add(list: List<BestStockModel>) {
//        items.clear()
//        items.addAll(list)
//        notifyItemRangeChanged(0, list.size)
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.stock_best_stock_item_model, parent, false)
//        return ViewHolder(
//            view
//        )
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])
//
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        fun bind(model: BestStockModel) {
//            itemView.rootView.setOnClickListener {
//                click.onClick(id = model.id, name = model.name)
//            }
//            itemView.title.text = model.name
//            itemView.price.text = Arrange().persianConcatenate(first = model.price, end = " ریال ")
//            itemView.full_name.text = model.full_name
//            itemView.percentage.text =
//
//                Arrange().persianConcatenate(
//                    first = Arrange().persianConcatenate(
//                        first = "(",
//                        middle = model.profit.toString(),
//                        end = "%"
//                    ), end = ")"
//                )
//
//            itemView.percentage.setTextColor(
//                if (model.profit > 0)
//                    ContextCompat.getColor(
//                        itemView.context,
//                        R.color.dark_green
//                    )
//                else
//                    ContextCompat.getColor(
//                        itemView.context,
//                        R.color.red
//                    )
//            )
//        }
//    }
//
//

//}
//
//
