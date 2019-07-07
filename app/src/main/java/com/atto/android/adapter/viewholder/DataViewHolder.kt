package com.atto.android.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.atto.android.model.Data

/**
 * Created by leekijung on 2019. 4. 21..
 */

abstract class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindData(data: Data)
}

