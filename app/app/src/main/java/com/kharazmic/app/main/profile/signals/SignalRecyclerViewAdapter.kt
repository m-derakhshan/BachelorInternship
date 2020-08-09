package com.kharazmic.app.main.profile.signals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kharazmic.app.R
import kotlinx.android.synthetic.main.profile_signals_model.view.*


class SignalRecyclerViewAdapter : RecyclerView.Adapter<SignalRecyclerViewAdapter.ViewHolder>() {

    private val items = ArrayList<SignalsModel>()
    lateinit var onClick: SignalsOnClickListener


    fun addData(data: List<SignalsModel>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_signals_model, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.binding(items[position])


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun binding(model: SignalsModel) {
            itemView.rootView.setOnClickListener {
                onClick.onClick(id = model.id, category = model.category)
            }
            itemView.title.text = model.title
        }

    }
}


interface SignalsOnClickListener {

    fun onClick(id: String, category: String)
}