package com.kharazmic.app.main.stock.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import kotlinx.android.synthetic.main.message_recycler_item_model.view.*

class MessageRecyclerAdapter : ListAdapter<MessageModel, MessageRecyclerAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<MessageModel>() {
        override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem == newItem
        }

    }
) {


    private val collection = ExpansionLayoutCollection().apply {
        openOnlyOne(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_recycler_item_model, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        collection.add(holder.itemView.expansion_layout)
        holder.bind(getItem(position))
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: MessageModel) {
            itemView.symbol.text = model.symbol
            itemView.full_name.text =
                Arrange().persianConcatenate(first = "(", middle = model.name, end = ")")
            itemView.message.text = model.message
            itemView.date.text = Arrange().persianConverter(model.date)
            itemView.time.text = Arrange().persianConverter(model.time)
        }
    }


}