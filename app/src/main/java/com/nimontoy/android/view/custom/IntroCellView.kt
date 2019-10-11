package com.nimontoy.android.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nimontoy.android.R

class IntroCellView : ConstraintLayout {
    companion object {
        private val VIEW_VISIBLE = "visible"
        private val VIEW_INVISIBLE = "invisible"
        private val VIEW_GONE = "gone"
    }

    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.mypage_intro_cell, this, false) }

    val introText by lazy<TextView> { findViewById(R.id.intro_text) }
    val numberText by lazy<TextView> { findViewById(R.id.number_text) }
    val newBadge by lazy<TextView> { findViewById(R.id.new_badge) }

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IntroCellView)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IntroCellView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val intro = typedArray.getString(R.styleable.IntroCellView_intro_text)
        introText.text = intro

        val number = typedArray.getString(R.styleable.IntroCellView_number_text)
        numberText.text = number

        typedArray.recycle()
    }

    fun setIntroNum (number : String) {
        numberText.text = number
        newBadge.visibility = View.GONE
    }
}