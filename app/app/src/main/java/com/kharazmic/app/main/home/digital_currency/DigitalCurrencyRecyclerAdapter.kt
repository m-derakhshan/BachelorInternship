package com.kharazmic.app.main.home.digital_currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import kotlinx.android.synthetic.main.digital_currency_item_model.view.*

class DigitalCurrencyRecyclerAdapter :
    ListAdapter<DigitalCurrencyModel, DigitalCurrencyRecyclerAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<DigitalCurrencyModel>() {
            override fun areItemsTheSame(
                oldItem: DigitalCurrencyModel,
                newItem: DigitalCurrencyModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DigitalCurrencyModel,
                newItem: DigitalCurrencyModel
            ): Boolean {
                return oldItem == newItem
            }


        }) {

    lateinit var clickListener: DigitalCurrencyListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.digital_currency_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: DigitalCurrencyModel) {


            itemView.name.text = model.name

            itemView.price_dollar.text = Arrange().numberEnglishArrangement(model.price_dollar)
            itemView.price_dollar_change.text =
                Arrange().concatenate(
                    first = "(",
                    middle = model.price_dollar_change_24,
                    end = ")"
                )


            itemView.price_rial.text = Arrange().numberEnglishArrangement(model.price_rial)

            itemView.price_rial_change.text =
                Arrange().concatenate(
                    first = "(",
                    middle = model.price_rial_change_24,
                    end = ")"
                )


            itemView.price_rial_change.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (model.price_rial_change_24.toInt() > 0) R.color.dark_green else R.color.red
                )
            )


            itemView.price_dollar_change.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (model.price_dollar_change_24.toInt() > 0) R.color.dark_green else R.color.red
                )
            )




            Glide.with(itemView)
                .load(model.icon)
                .into(itemView.icon)

        }
    }


    interface DigitalCurrencyListener {
        fun onClick(model: DigitalCurrencyModel)
    }
}