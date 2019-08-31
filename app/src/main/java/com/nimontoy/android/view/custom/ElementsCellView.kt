package com.nimontoy.android.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nimontoy.android.R

class ElementsCellView : ConstraintLayout {
    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.elements_cell, this, false) }

    val btnElementsShare by lazy<ImageButton> { findViewById(R.id.btn_elements_share) }
    val btnElementsComment by lazy<ImageButton> { findViewById(R.id.btn_elements_comment) }
    val btnElementsLike by lazy<ImageButton> { findViewById(R.id.btn_elements_like) }

    val textElementsComment by lazy<TextView> { findViewById(R.id.text_elements_comment) }
    val textElementsLike by lazy<TextView> { findViewById(R.id.text_elements_like) }

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ElementsCellView)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ElementsCellView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        typedArray.recycle()
    }

    fun setTextComment (comments : String) {
        textElementsComment.text = comments
    }

    fun setTextComment (comments : Int) {
        textElementsComment.text = comments.toString()
    }

    fun setTextLike (likes : String) {
        textElementsLike.text = likes
    }

    fun setTextLike (likes : Int) {
        textElementsLike.text = likes.toString()
    }

    fun setBtnShareClickListener (listener : OnClickListener) {
        btnElementsShare.setOnClickListener(listener)
    }

    fun setBtnCommentClickListener (listener : OnClickListener) {
        btnElementsComment.setOnClickListener(listener)
        textElementsComment.setOnClickListener(listener)
    }

    fun setBtnLikeClickListener (listener : OnClickListener) {
        btnElementsLike.setOnClickListener(listener)
        textElementsLike.setOnClickListener(listener)
    }

}