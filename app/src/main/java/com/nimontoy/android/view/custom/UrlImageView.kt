package com.nimontoy.android.view.custom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.nimontoy.android.AttoApplication
import com.nimontoy.android.R

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import org.jetbrains.anko.runOnUiThread

/**
 * Created by leekijung on 2019. 4. 21..
 */

class UrlImageView : AppCompatImageView {
    private val defaultPlaceHolderId: Int = 0

    private var square = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        square = context.obtainStyledAttributes(attrs, R.styleable.UrlImageView).getBoolean(R.styleable.UrlImageView_square, false)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        square = context.obtainStyledAttributes(attrs, R.styleable.UrlImageView).getBoolean(R.styleable.UrlImageView_square, false)
    }

    fun render(url: String) {
        AttoApplication.appContext?.let {
            Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(false)
                    .centerCrop().format(DecodeFormat.PREFER_ARGB_8888))
                .into(this)
        }
    }

    fun renderFit(url: String) {
        AttoApplication.appContext?.let {
            Glide.with(it)
                .asBitmap()
                .load(Uri.parse(url))
                .apply(RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(false)
                    .fitCenter().format(DecodeFormat.PREFER_ARGB_8888))
                .into(this)
        }
    }

    fun renderWithCorner(url: String, corner: Int) {
        AttoApplication.appContext?.let {
            Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(false)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .centerCrop()
                    .transforms(CenterCrop(), RoundedCorners(corner)))
                .into(this)
        }
    }

    fun renderMemoryCache(url: String) {
        AttoApplication.appContext?.let {
            Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .dontTransform().format(DecodeFormat.PREFER_ARGB_8888))
                .into(this)
        }
    }

    fun renderBackground(url: String) {
        AttoApplication.appContext?.let {
            Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(false)
                    .centerCrop().format(DecodeFormat.PREFER_ARGB_8888)
                    .encodeFormat(Bitmap.CompressFormat.PNG))
                .listener(object : RequestListener<Bitmap>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        val bitmapDrawable = BitmapDrawable(resources, resource)
                        this@UrlImageView.background = bitmapDrawable
                        return false
                    }

                }).submit()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (square) {
            val width = this.measuredWidth
            this.layoutParams.height = width
            this.requestLayout()
        }
    }
}