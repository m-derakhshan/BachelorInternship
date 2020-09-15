package com.kharazmic.app.main.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.kharazmic.app.Arrange
import com.kharazmic.app.R
import com.kharazmic.app.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail)

        val info = intent.getParcelableExtra<NewsAdapterModel>("info")!!

        binding.date.text = Arrange().persianConverter(info.date)
        binding.title.text = HtmlCompat.fromHtml(info.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.description.text = HtmlCompat.fromHtml(info.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.source.text = info.source
        Glide.with(this)
            .load(info.image)
            .placeholder(R.mipmap.logo)
            .into(binding.image)
        binding.back.setOnClickListener {
            onBackPressed()
        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}