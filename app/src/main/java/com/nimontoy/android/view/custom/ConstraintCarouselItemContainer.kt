package com.nimontoy.android.view.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

class ConstraintCarouselItemContainer: ConstraintLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var scale: Float = 1f

    fun setItemScale(scale: Float) {
        this.scale = scale
        this.invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        val w = this.width
        val h = this.height
        canvas?.scale(scale, scale, w/2f, h/2f)
        super.onDraw(canvas)
    }
}