package com.kharazmic.app.main.profile.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kharazmic.app.R
import kotlinx.android.synthetic.main.setting_item_model.view.*

class SettingRecyclerAdapter : RecyclerView.Adapter<SettingRecyclerAdapter.ViewHolder>() {

    private val items = ArrayList<SettingModel>()


    fun add(list: ArrayList<SettingModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.setting_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.binding(items[position])


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun binding(model: SettingModel) {
            itemView.rootView.setOnClickListener {

            }
            itemView.title.text = model.title
            itemView.icon.setImageDrawable(model.icon)
        }
    }

}