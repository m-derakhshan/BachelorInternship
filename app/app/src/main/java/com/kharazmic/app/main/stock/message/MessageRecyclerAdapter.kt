package com.kharazmic.app.main.stock.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kharazmic.app.R

class MessageRecyclerAdapter : RecyclerView.Adapter<MessageRecyclerAdapter.ViewHolder>() {

    private var items = ArrayList<MessageModel>()

    fun add(list: ArrayList<MessageModel>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_recycler_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: MessageModel) {

        }
    }


}