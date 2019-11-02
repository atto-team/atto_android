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

class UserCellView : ConstraintLayout{

    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.profile_cell, this, false) }

    private val profileImage by lazy<ImageView> { findViewById(R.id.profile_image) }
    private val usernameText by lazy<TextView> { findViewById(R.id.username_text) }
    private val dateText by lazy<TextView> { findViewById(R.id.date_text) }


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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserCellView)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserCellView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val imageProfileSrc = typedArray.getResourceId(R.styleable.UserCellView_image_profile_src, 0)
        profileImage.setImageResource(imageProfileSrc)
        val userName = typedArray.getString(R.styleable.UserCellView_username_text)
        usernameText.text = userName

        val date = typedArray.getString(R.styleable.UserCellView_date)
        dateText.text = date

        typedArray.recycle()
    }

    fun setUserNameText (userName : String) {
        usernameText.text = userName
    }

    fun setDateTimeText (date : String) {
        dateText.text = date
    }
}