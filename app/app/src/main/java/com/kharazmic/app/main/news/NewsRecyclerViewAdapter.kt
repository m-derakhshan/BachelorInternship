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
    lateinit var hashTagListener: HashTagListener


    fun add(list: ArrayList<NewsAdapterModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun addMore(list: ArrayList<NewsAdapterModel>) {
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

        holder.itemView.tags.setOnClickListener {
            hashTagListener.onHashTagClicked(items[position].tags?.firstOrNull() ?: "")
        }
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.hashCode().toLong()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun binding(model: NewsAdapterModel) {


            val tags = if (model.tags?.firstOrNull().isNullOrEmpty()) ""
            else Arrange().persianConcatenate(first = "#", end = model.tags?.first())


            itemView.tags.visibility = if (tags.isNotEmpty()) View.VISIBLE else View.GONE
            itemView.tags.text = tags



            itemView.setOnClickListener {
                onClick.onClick(model)
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


    interface HashTagListener {
        fun onHashTagClicked(hashTag: String)
    }
}