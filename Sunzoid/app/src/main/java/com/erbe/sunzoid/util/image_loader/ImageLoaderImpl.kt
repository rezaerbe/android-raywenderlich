package com.erbe.sunzoid.util.image_loader

import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageLoaderImpl(private val picasso: Picasso) : ImageLoader {

    override fun load(imageResource: String, target: ImageView) {
        picasso.load(imageResource).into(target)
    }
}