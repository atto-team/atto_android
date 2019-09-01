package com.nimontoy.android.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager

@SuppressLint("CheckResult")
class PagingOptionViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    val TAG = "PagingOptionViewPager"

    private lateinit var view: View
    var isPagingEnabled: Boolean = false

    init {
        isPagingEnabled = true
        initView()
    }

    private fun initView() {}

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        try {
            return isPagingEnabled && super.onInterceptTouchEvent(event)
        } catch (ignore: IllegalArgumentException) {
        }

        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onTouchEvent(event)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item)
    }

    fun measureCurrentView(currentView: View) {
        view = currentView
        requestLayout()
    }

    fun measureFragment(view: View?): Int {
        if (view == null)
            return 0

        view.measure(0, 0)
        return view.measuredHeight
    }

}
