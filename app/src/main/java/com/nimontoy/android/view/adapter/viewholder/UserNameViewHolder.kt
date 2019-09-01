package com.nimontoy.android.view.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import com.nimontoy.android.viewmodel.cell.DataCellViewModel

/**
 * Created by leesujung on 2019. 8. 18..
 */
class UserNameViewHolder (itemView: View) : DataViewHolder(itemView) {
    override fun reset() {
    }

    override fun bindViews(data: Data) {
    }

    val user_name: TextView = itemView.findViewById(R.id.user_name)

    override fun bindData(data: Data, viewModel: DataCellViewModel) {

    }
}