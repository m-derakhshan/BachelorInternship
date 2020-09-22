package com.kharazmic.app.main.home.desk.constant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kharazmic.app.R
import kotlinx.android.synthetic.main.constant_income_item_model.view.*


class ConstantIncomeRecyclerAdapter :
    ListAdapter<ConstantIncomeModel, ConstantIncomeRecyclerAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<ConstantIncomeModel>() {
            override fun areItemsTheSame(
                oldItem: ConstantIncomeModel,
                newItem: ConstantIncomeModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ConstantIncomeModel,
                newItem: ConstantIncomeModel
            ): Boolean {
                return oldItem == newItem
            }
        }) {

    private val colorGray = 0
    lateinit var listener: ConstantIncomeListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.constant_income_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.rootView.setOnClickListener {
            listener.onClick(getItem(position))
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: ConstantIncomeModel) {

            itemView.root.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (itemViewType == colorGray) R.color.light_violet
                    else
                        R.color.dark_violet
                )
            )

            itemView.name.text = model.name
            itemView.profit.text = model.one_month
            itemView.risk.text = model.riskCriteriaBeta

        }
    }


    override fun getItemViewType(position: Int): Int {

        return if (position % 2 == 0) 0 else 1
    }

    interface ConstantIncomeListener {
        fun onClick(model: ConstantIncomeModel)
    }

}

