package com.nimontoy.android.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.nimontoy.android.R
import kotlinx.android.synthetic.main.image_cell.view.*


class ImageCellView : ConstraintLayout {
    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.image_cell, this, false) }

    val firstIamge by lazy<UrlImageView> { findViewById(R.id.first_image) }
    val secondImage by lazy<UrlImageView> { findViewById(R.id.second_image) }
    val thirdImage by lazy<UrlImageView> { findViewById(R.id.third_image) }
    val extraText by lazy<TextView> { findViewById(R.id.extra_text) }

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

    fun setImages (imageList : MutableList<String?>) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(feed_cell_constraint)

        firstIamge.visibility = View.GONE
        secondImage.visibility = View.GONE
        thirdImage.visibility = View.GONE
        extraText.visibility = View.GONE
        when (imageList.size) {
            0 -> { }
            1 -> {
                firstIamge.layoutParams = (firstIamge.layoutParams as LayoutParams).apply {
                    dimensionRatio = "2:1"
                }
                setImageFirst(imageList[0])
                firstIamge.visibility = View.VISIBLE
            }
            2 -> {
                firstIamge.layoutParams = (firstIamge.layoutParams as LayoutParams).apply {
                    dimensionRatio = "1:1"
                }
                secondImage.layoutParams = (secondImage.layoutParams as LayoutParams).apply {
                    matchConstraintPercentHeight = 1f
                }
                setImageFirst(imageList[0])
                setImageSecond(imageList[1])
                firstIamge.visibility = View.VISIBLE
                secondImage.visibility = View.VISIBLE
            }
            3 -> {
                constraintSet.applyTo(feed_cell_constraint)

                setImageFirst(imageList[0])
                setImageSecond(imageList[1])
                setImageThird(imageList[2])

                firstIamge.visibility = View.VISIBLE
                secondImage.visibility = View.VISIBLE
                thirdImage.visibility = View.VISIBLE
            }
            else -> {
                constraintSet.applyTo(feed_cell_constraint)

                setImageFirst(imageList[0])
                setImageSecond(imageList[1])
                setImageThird(imageList[2])
                setTextExtra('+' + (imageList.size - 3).toString())

                firstIamge.visibility = View.VISIBLE
                secondImage.visibility = View.VISIBLE
                thirdImage.visibility = View.VISIBLE
                extraText.visibility = View.VISIBLE
            }
        }
    }

    fun setImageFirst(imageUrl : String?) {
        imageUrl?.let { firstIamge.render(it) }
    }

    fun setImageSecond(imageUrl : String?) {
        imageUrl?.let { secondImage.render(it) }
    }

    fun setImageThird(imageUrl : String?) {
        imageUrl?.let { thirdImage.render(it) }
    }

    fun setTextExtra(text : String) {
        extraText.text = text
    }
}