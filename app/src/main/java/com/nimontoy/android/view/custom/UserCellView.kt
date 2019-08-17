package com.nimontoy.android.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
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

    val imageProfile by lazy<ImageView> { findViewById(R.id.image_profile) }
    val textUserName by lazy<TextView> { findViewById(R.id.text_userName_profile) }
    val textDate by lazy<TextView> { findViewById(R.id.text_date_profile) }


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
        var imageProfileSrc = typedArray.getResourceId(R.styleable.UserCellView_image_profile_src, 0)
        imageProfile.setImageResource(imageProfileSrc)
        var userName = typedArray.getString(R.styleable.UserCellView_username_text)
        textUserName.text = userName

        var date = typedArray.getString(R.styleable.UserCellView_date)
        textDate.text = date

        typedArray.recycle()
    }

    fun setImageProfile (image_res : Int) {
        imageProfile.setImageResource(image_res)
    }

    fun setUserNameText (userName : String) {
        textUserName.text = userName
    }

    fun setDateText (date : String) {
        textDate.text = date
    }
}