package com.nimontoy.android.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.viewpager2.widget.ViewPager2
import com.nimontoy.android.R
import com.nimontoy.android.model.home.Toy
import com.nimontoy.android.model.home.ToyLineUp
import com.nimontoy.android.view.adapter.CarouselPagerRecyclerAdapter
import java.lang.Exception
import java.lang.Math.abs

class HomeToyCell: LinearLayout {
    private val layoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val view: View by lazy { layoutInflater.inflate(R.layout.home_toy_cell, this, false) }

    val toyPager by lazy { findViewById<ViewPager2>(R.id.home_toy_pager) }
    val toyNavigation by lazy { findViewById<Group>(R.id.home_toy_name_navigator) }
    val toyNaviLeft by lazy { findViewById<ImageButton>(R.id.home_toy_left_navi) }
    val toyNaviRight by lazy { findViewById<ImageButton>(R.id.home_toy_right_navi) }
    val toyName by lazy { findViewById<TextView>(R.id.home_toy_name) }


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
        toyPager.adapter = CarouselPagerRecyclerAdapter(mutableListOf())
        toyPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        toyPager.offscreenPageLimit = 1 //default = -1 1로 변경해줘야 다음꺼 보이게 할 수 있다는 듯 함

    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeToyCell)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeToyCell, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        typedArray.recycle()
    }


    fun setToys(toyList: MutableList<Toy>) {
        val toy = toyList[0]
        if (toy is ToyLineUp) {
            toyNavigation.visibility = View.GONE
        } else {
            toyNavigation.visibility = View.VISIBLE
        }
        (toyPager.adapter as CarouselPagerRecyclerAdapter).setToyList(toyList)
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // position(중심과의거리)에 따라 세로 scale(크기)를 변경해준다. cf. 중앙일 때 position = 0 \ 왼쪽으로 한페이지 거리 = -1 \ 오른쪽으로 한페이지 거리 = 1
            page.scaleY = 1 - (0.4f * abs(position))
            // 필요에 따라 알파값(투명도)도 조절할 수 있으나 현재 프로젝트에선 불필요
            // page.alpha = 0.25f + (1 - abs(position))
            // 중심에 선택된 페이지를 가장 위에 두기 위해 elevation을 조정...
            page.elevation = 10f - (10f * abs(position))
        }
        toyPager.setPageTransformer(pageTransformer)
    }

    fun setOnPageChangeCallback(pageChangeCallback: ViewPager2.OnPageChangeCallback) {
        toyPager.registerOnPageChangeCallback(pageChangeCallback)
    }
}