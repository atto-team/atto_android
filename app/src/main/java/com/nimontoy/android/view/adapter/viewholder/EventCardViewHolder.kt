package com.nimontoy.android.view.adapter.viewholder

import android.view.View
import kotlinx.android.synthetic.main.viewholder_event_card.*
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.mypage.EventCard
import com.nimontoy.android.view.custom.EventCardCellView
import com.nimontoy.android.viewmodel.cell.DataCellViewModel

class EventCardViewHolder (itemView: View) : DataViewHolder(itemView) {
    private val eventCardCell by lazy { itemView.findViewById<EventCardCellView>(R.id.event_card) }

    override fun reset() {
        // TODO 매번 리싸이클 시 뷰 초기화 부탁
    }

    override fun bindData(data: Data, viewModel: DataCellViewModel) {
        super.bindData(data, viewModel)
        if (data is EventCard) {
            eventCardCell.setEventText(data.title)
            eventCardCell.setButton(data.title)
        }
    }

    override fun bindViews(data: Data) {
        // TODO 클릭 이벤트 작성
    }
}