package com.nimontoy.android.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nimontoy.android.R

class EventCardCellView : ConstraintLayout {
    companion object {
        private val VIEW_VISIBLE = "visible"
        private val VIEW_INVISIBLE = "invisible"
        private val VIEW_GONE = "gone"
    }

    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.event_card_cell, this, false) }

    val eventText by lazy<TextView> { findViewById(R.id.button_text) }
    val arrowButton by lazy<Button> { findViewById(R.id.arrow_button) }
    val moreButton by lazy<Button> { findViewById(R.id.more_button) }


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
        val text = typedArray.getString(R.styleable.EventCardCellView_event_text)
        eventText.text = text

        //var visibility = typedArray.getString(R.styleable.ToysCardCellView_effect_visibility)
        //setVisible(arrowButton, visibility)

        typedArray.recycle()
    }

    fun setEventText(text : String) {
        eventText.text = text
    }

    fun setButton(cardText : String?) {
        arrowButton.visibility = View.GONE
        moreButton.visibility = View.GONE
        when (cardText) {
            "My Post", "My Comments"-> {
                arrowButton.visibility = View.VISIBLE
                moreButton.visibility = View.GONE
            }
            "My Toys" ->{
                arrowButton.visibility = View.GONE
                moreButton.visibility = View.VISIBLE
            }
            else -> {
                arrowButton.visibility = View.GONE
                moreButton.visibility = View.GONE
            }
        }
    }

}