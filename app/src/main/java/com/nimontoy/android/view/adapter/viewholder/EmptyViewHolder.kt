package com.nimontoy.android.view.adapter.viewholder

import android.view.View
import com.nimontoy.android.model.Data
import com.nimontoy.android.viewmodel.cell.DataCellViewModel

/**
 * Created by leekijung on 2019. 4. 21..
 */

class EmptyViewHolder(itemView: View) : DataViewHolder(itemView) {
    override fun reset() {}

    override fun bindData(data: Data, viewModel: DataCellViewModel) {}

    override fun bindViews(data: Data) {}
}