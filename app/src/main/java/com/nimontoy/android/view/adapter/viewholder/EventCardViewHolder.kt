package com.nimontoy.android.view.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.mypage.EventCard
import com.nimontoy.android.view.custom.EventCardCellView
import com.nimontoy.android.viewmodel.cell.DataCellViewModel

class EventCardViewHolder (itemView: View) : DataViewHolder(itemView) {
    private val EventCardCell by lazy { itemView.findViewById<EventCardCellView>(R.id.event_card) }

    override fun reset() {
    }

    override fun bindData(data: Data, viewModel: DataCellViewModel) {
        super.bindData(data, viewModel)
        if (data is EventCard) {
            val eventCard = data
            EventCardCell.setEventText(eventCard.title)
            EventCardCell.setButton(eventCard.title)
        }
    }

    override fun bindViews(data: Data) {
    }
}