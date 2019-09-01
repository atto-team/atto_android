package com.nimontoy.android.view.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import com.nimontoy.android.viewmodel.cell.DataCellViewModel

class EventCardViewHolder (itemView: View) : DataViewHolder(itemView) {

    val title_event_card: TextView = itemView.findViewById(R.id.title_event_card)

    override fun reset() {
    }

    override fun bindData(data: Data, viewModel: DataCellViewModel) {
    }

    override fun bindViews(data: Data) {
    }
}