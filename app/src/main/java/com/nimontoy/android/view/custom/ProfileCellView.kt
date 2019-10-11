package com.nimontoy.android.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nimontoy.android.R

class ProfileCellView : ConstraintLayout{
    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.mypage_profile_cell, this, false) }

    val profileImage by lazy<UrlImageView> { findViewById(R.id.profileImageView) }
    val QRcodeText by lazy<TextView> { findViewById(R.id.QRcode) }
    val followText by lazy<TextView> { findViewById(R.id.follow) }
    val followerText by lazy<TextView> { findViewById(R.id.follower) }


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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProfileCellView)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProfileCellView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        // 동그란 프로필
        profileImage.setBackground(ShapeDrawable(OvalShape()))
        profileImage.setClipToOutline(true)

        val image = typedArray.getResourceId(R.styleable.ProfileCellView_profile_image, 0)
        profileImage.setImageResource(image)

        val follow = typedArray.getString(R.styleable.ProfileCellView_follow_text)
        followText.text = follow

        val follower = typedArray.getString(R.styleable.ProfileCellView_follower_text)
        followerText.text = follower

        typedArray.recycle()
    }

    fun setProfileImage(imageUrl : String?) {
        imageUrl?.let { profileImage.render(it) }
    }

    fun setProfileText (follow : String, follower : String) {
        followText.text = follow
        followerText.text = follower
    }

    fun setQRcodeClickListener (listener : OnClickListener) {
        QRcodeText.setOnClickListener(listener)
    }
}