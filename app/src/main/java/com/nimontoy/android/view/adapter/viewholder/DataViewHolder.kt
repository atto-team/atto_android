package com.nimontoy.android.view.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nimontoy.android.model.Data
import com.nimontoy.android.viewmodel.cell.DataCellViewModel

/**
 * Created by leekijung on 2019. 4. 21..
 */

abstract class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun reset()

    open fun bindData(data: Data, viewModel: DataCellViewModel) {
        reset()
    }

    abstract fun bindViews(data: Data)
}

