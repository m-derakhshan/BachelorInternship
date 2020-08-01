package com.kharazmic.app.main.profile

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView


@BindingAdapter("loadImage")
fun CircleImageView.loadImage(url: String?) {
    Log.i("Log", "url is $url")
    url?.let { address ->
        Glide.with(context)
            .load(address)
            .into(this)
    }
}