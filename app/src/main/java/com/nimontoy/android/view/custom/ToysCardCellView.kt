package com.nimontoy.android.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nimontoy.android.R


class ToysCardCellView : ConstraintLayout {
    companion object {
        private val VIEW_VISIBLE = "visible"
        private val VIEW_INVISIBLE = "invisible"
        private val VIEW_GONE = "gone"
    }

    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.mypage_toys_card_cell, this, false) }

    val toyImage by lazy<UrlImageView> { findViewById(R.id.toy_card) }
    val effect by lazy<UrlImageView> { findViewById(R.id.effect) }
    val toyText by lazy<TextView> { findViewById(R.id.toy_text) }


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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToysCardCellView)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToysCardCellView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val image = typedArray.getResourceId(R.styleable.ToysCardCellView_toy_image, 0)
        toyImage.setImageResource(image)

        val text = typedArray.getString(R.styleable.ToysCardCellView_toy_text)
        toyText.text = text

        var visibility = typedArray.getString(R.styleable.ToysCardCellView_effect_visibility)
        setVisible(effect, visibility)

        typedArray.recycle()
    }

    fun setToy(imageUrl : String?, text : String?) {
        imageUrl?.let { toyImage.render(it) }
        toyText.text = text
    }

    private fun setVisible(view: View, visibility: String?) {
        if (visibility != null) {
            when (visibility) {
                VIEW_VISIBLE -> view.visibility = View.VISIBLE
                VIEW_INVISIBLE -> view.visibility = View.INVISIBLE
                VIEW_GONE -> view.visibility = View.GONE
                else -> view.visibility = View.VISIBLE
            }
        }
    }
}