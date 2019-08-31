package com.nimontoy.android.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.nimontoy.android.R

class ImageCellView : ConstraintLayout {
    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.image_cell, this, false) }

    val imageFirst by lazy<ImageView> { findViewById(R.id.imageFirst) }
    val imageSecond by lazy<ImageView> { findViewById(R.id.imageSecond) }
    val imageThird by lazy<ImageView> { findViewById(R.id.imageThird) }

    val textExtra by lazy<TextView> { findViewById(R.id.textExtra) }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
        getAttrs(attrs, defStyleAttr)
    }

    private fun initView() {
        addView(view)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageCellView)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageCellView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        typedArray.recycle()
    }

    fun setImage (image_list : ArrayList<Int>) {
        when (image_list.size) {
            1 -> {
                setImageFirst(image_list[0])
                imageSecond.visibility = View.GONE
                imageThird.visibility = View.GONE
                textExtra.visibility = View.GONE
            }
            2 -> {
                setImageFirst(image_list[0])
                setImageSecond(image_list[1])

                imageThird.visibility = View.GONE
                textExtra.visibility = View.GONE
            }
            3 -> {
                setImageFirst(image_list[0])
                setImageSecond(image_list[1])
                setImageThird(image_list[2])

                changeImageWidth()

                textExtra.visibility = View.GONE
            }
            else -> {
                setImageFirst(image_list[0])
                setImageSecond(image_list[1])
                setImageThird(image_list[2])

                changeImageWidth()

                setTextExtra('+' + (image_list.size - 3).toString())
                setBlackBackground()
            }
        }
    }

    fun setImageFirst (image_res : Int) {
        imageFirst.setImageResource(image_res)
    }

    fun setImageSecond (image_res : Int) {
        imageSecond.setImageResource(image_res)
    }

    fun setImageThird (image_res : Int) {
        imageThird.setImageResource(image_res)
    }

    fun setTextExtra (text_extra : String) {
        textExtra.text = text_extra
    }

    fun setBlackBackground () {
        imageThird.alpha = 0.9F
        imageThird.setBackgroundResource(R.color.colorBlackTransparent)
    }

    fun changeImageWidth () {
        val set = ConstraintSet()
        set.clone(this)
        set.constrainPercentWidth(R.id.imageFirst, 0.75F)
        set.applyTo(this)
    }
}