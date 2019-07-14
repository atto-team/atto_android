package com.atto.android.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.atto.android.AttoApplication
import com.atto.android.R

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

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
        Glide.with(AttoApplication.appContext)
                .asBitmap()
                .load(url)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(false)
                        .centerCrop().format(DecodeFormat.PREFER_ARGB_8888))
                .into(this)
    }

    fun renderWithCorner(url: String, corner: Int) {
        Glide.with(AttoApplication.appContext)
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

    fun renderMemoryCache(url: String) {
        Glide.with(AttoApplication.appContext)
                .asBitmap()
                .load(url)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .dontTransform().format(DecodeFormat.PREFER_ARGB_8888))
                .into(this)
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