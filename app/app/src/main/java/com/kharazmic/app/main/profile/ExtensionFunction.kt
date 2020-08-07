package com.kharazmic.app.main.profile


import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kharazmic.app.R
import de.hdodenhof.circleimageview.CircleImageView


@BindingAdapter("loadImage")
fun CircleImageView.loadImage(url: String?) {
    url?.let { address ->
        Glide.with(context)
            .load(address)
            .placeholder(R.mipmap.default_image)
            .into(this)
    }
}