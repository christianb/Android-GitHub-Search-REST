package com.bunk.github.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

class GlideProvider {
    class Builder {
        private lateinit var url: String
        private val headers: MutableMap<String, String> = mutableMapOf()

        fun url(url: String): Builder {
            this.url = url
            return this
        }

        fun addHeader(key: String, value: String): Builder {
            headers.put(key, value)
            return this
        }

        fun into(imageView: ImageView) {
            val lazyHeadersBuilder = LazyHeaders.Builder()
            headers.forEach { entry ->
                lazyHeadersBuilder.addHeader(entry.key, entry.value)
            }

            val glideUrl = GlideUrl(url, lazyHeadersBuilder.build())

            Glide.with(imageView).load(glideUrl).into(imageView)
        }
    }
}