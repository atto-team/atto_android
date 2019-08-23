package com.nimontoy.android.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nimontoy.android.R

class CardComponentCellView : ConstraintLayout {
    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.card_component_cell, this, false) }

    private val iconCardComponent by lazy<ImageView> { findViewById(R.id.icon_card_component) }
    private val textCardComponent by lazy<TextView> { findViewById(R.id.text_card_component) }

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardComponentCellView)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardComponentCellView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val mode = typedArray.getString(R.styleable.CardComponentCellView_mode)
        when (mode) {
            "delete" -> {
                setIconCardComponent(R.drawable.icon_discard)
                setTextCardComponent("Delete")
            }
            "share" -> {
                setIconCardComponent(R.drawable.icon_share)
                setTextCardComponent("Share")
            }
            "modify" -> {
                setIconCardComponent(R.drawable.icon_modify)
                setTextCardComponent("Modify")
            }
        }
        typedArray.recycle()
    }

    fun setIconCardComponent (image_res : Int) {
        iconCardComponent.setImageResource(image_res)
    }

    fun setTextCardComponent (text : String) {
        textCardComponent.text = text
    }
}