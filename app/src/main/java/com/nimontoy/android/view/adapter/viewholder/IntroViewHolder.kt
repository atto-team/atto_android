package com.nimontoy.android.view.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.mypage.Profile
import com.nimontoy.android.view.custom.IntroCellView
import com.nimontoy.android.view.custom.ToysCardCellView
import com.nimontoy.android.viewmodel.cell.DataCellViewModel

class IntroViewHolder (itemView: View) : DataViewHolder(itemView) {
    private val activityText by lazy { itemView.findViewById<IntroCellView>(R.id.activity_intro) }
    private val likeText by lazy { itemView.findViewById<IntroCellView>(R.id.like_intro) }
    private val pointText by lazy { itemView.findViewById<IntroCellView>(R.id.point_intro) }

    override fun reset() {
    }

    override fun bindData(data: Data, viewModel: DataCellViewModel) {
        super.bindData(data, viewModel)
        if (data is Profile) {
            val profile = data

            if(profile.activity>9999) activityText.setIntroNum("999+")
            else activityText.setIntroNum(profile.activity.toString())

            if(profile.like>9999) likeText.setIntroNum("999+")
            else likeText.setIntroNum(profile.like.toString())

            if(profile.point>9999) pointText.setIntroNum("999+")
            else pointText.setIntroNum(profile.point.toString())

        }
    }

    override fun bindViews(data: Data) {
    }
}