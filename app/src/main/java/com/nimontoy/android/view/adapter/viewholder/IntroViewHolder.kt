package com.nimontoy.android.view.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.mypage.Profile
import com.nimontoy.android.view.custom.IntroCellView
import com.nimontoy.android.view.custom.ToysCardCellView
import com.nimontoy.android.viewmodel.cell.DataCellViewModel

const val limit = 9999
class IntroViewHolder (itemView: View) : DataViewHolder(itemView) {
    private val activityText by lazy { itemView.findViewById<IntroCellView>(R.id.activity_intro) }
    private val likeText by lazy { itemView.findViewById<IntroCellView>(R.id.like_intro) }
    private val pointText by lazy { itemView.findViewById<IntroCellView>(R.id.point_intro) }

    override fun reset() {
        activityText.setIntroNum("0")
        likeText.setIntroNum("0")
        pointText.setIntroNum("0")
    }

    override fun bindData(data: Data, viewModel: DataCellViewModel) {
        super.bindData(data, viewModel)
        if (data is Profile) {
            if(data.activity>limit) activityText.setIntroNum("999+")
            else activityText.setIntroNum(data.activity.toString())

            if(data.like>limit) likeText.setIntroNum("999+")
            else likeText.setIntroNum(data.like.toString())

            if(data.point>limit) pointText.setIntroNum("999+")
            else pointText.setIntroNum(data.point.toString())
        }
    }

    override fun bindViews(data: Data) {
        //Todo 클릭 이벤트 작성
    }
}