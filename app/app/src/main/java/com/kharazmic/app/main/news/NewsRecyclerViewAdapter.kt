package com.kharazmic.app.main.news

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import com.kharazmic.app.main.NewsTutorialClickListener
import kotlinx.android.synthetic.main.news_recycler_model.view.*

class NewsRecyclerViewAdapter : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    private val items = ArrayList<NewsAdapterModel>()
    lateinit var onClick: NewsTutorialClickListener

    fun add(list: ArrayList<NewsAdapterModel>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_recycler_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(items[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun binding(model: NewsAdapterModel) {


            itemView.setOnClickListener {
                onClick.onClick(model.id)
            }


            itemView.title.text = model.title
            itemView.description.text = model.description
            itemView.date.text = Arrange().persianConverter(model.date)
            itemView.source.text = model.source

            Glide.with(itemView.context)
                .load(model.image)
                .placeholder(R.mipmap.logo)
                .into(itemView.image)

        }
    }

}