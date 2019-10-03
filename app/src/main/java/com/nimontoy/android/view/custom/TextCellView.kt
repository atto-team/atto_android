package com.nimontoy.android.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nimontoy.android.R

class TextCellView : ConstraintLayout {
    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.text_cell, this, false) }

    private val textCell by lazy<TextView> { findViewById(R.id.text_cell) }

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextCellView)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextCellView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val text = typedArray.getString(R.styleable.TextCellView_text)
        setTextCell(text)
        typedArray.recycle()
    }

    fun setTextCell (text : String?) {
        textCell.text = text ?: "null"
    }
}