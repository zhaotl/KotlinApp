package com.ztl.kotlin.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ztl.kotlin.R

object GlideLoader {

    fun load(context: Context?, url: String?, imageView: ImageView?) {
        context ?: return

        imageView?.apply {
            Glide.with(context).clear(imageView)
            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.bg_placeholder)
            Glide.with(context).load(url).transition(DrawableTransitionOptions().crossFade())
                .apply(options)
                .into(imageView)
        }
    }
}