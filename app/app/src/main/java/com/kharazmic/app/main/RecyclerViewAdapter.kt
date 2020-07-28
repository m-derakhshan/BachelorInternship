package com.kharazmic.app.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kharazmic.app.R
import kotlinx.android.synthetic.main.main_item_model.view.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var items =  ArrayList<ItemMenuModel>()

    fun add(list: ArrayList<ItemMenuModel>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.binding(items[position])


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun binding(model: ItemMenuModel) {
            itemView.icon.setImageDrawable(model.icon)
            itemView.text.text = model.text
            itemView.root.setOnClickListener {

            }
        }
    }

}