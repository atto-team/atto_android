package com.nimontoy.android.view.adapter.viewholder


import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.home.Home
import com.nimontoy.android.view.custom.HomeToyCell
import com.nimontoy.android.viewmodel.cell.DataCellViewModel
import java.lang.Exception

class HomeViewHolder(itemView : View) : DataViewHolder(itemView) {

    private val TAG = "HomeViewHolder"
    private val seasonText by lazy { itemView.findViewById<TextView>(R.id.home_header_season) }
    private val moreButton by lazy { itemView.findViewById<Button>(R.id.home_header_more) }
    private val coin by lazy { itemView.findViewById<LottieAnimationView>(R.id.coin) }
    private val toyCarouselPager by lazy { itemView.findViewById<HomeToyCell>(R.id.home_toy_cell) }
    private val whoCreator by lazy { itemView.findViewById<TextView>(R.id.home_info_creator) }
    private val remainTime by lazy { itemView.findViewById<TextView>(R.id.home_info_remain_time) }

    override fun reset() {
        // TODO 매번 리싸이클 시 뷰 초기화 부탁
    }

    override fun bindData(data: Data, viewModel: DataCellViewModel) {
        super.bindData(data, viewModel)
        if (data is Home) {
            if (data.toysLineup.isNotEmpty()) {
                toyCarouselPager.setToys(data.toysLineup)
                val pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        try {
                            var toy = data.toysLineup[position]
                            //todo toy 값에 따른 코인 등 변화
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                toyCarouselPager.setOnPageChangeCallback(pageChangeListener)
            }
        }
    }

    override fun bindViews(data: Data) {

    }
}