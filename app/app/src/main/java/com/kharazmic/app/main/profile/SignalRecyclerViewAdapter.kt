package com.kharazmic.app.main.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.kharazmic.app.R
import kotlinx.android.synthetic.main.profile_signals_model.view.*


class SignalRecyclerViewAdapter : RecyclerView.Adapter<SignalRecyclerViewAdapter.ViewHolder>() {

    private val items = ArrayList<SignalsModel>()


    fun addData(data: ArrayList<SignalsModel>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_signals_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.binding(items[position])


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun binding(model: SignalsModel) {
            itemView.rootView.setOnClickListener {

            }
            itemView.title.text = model.title
        }

    }
}