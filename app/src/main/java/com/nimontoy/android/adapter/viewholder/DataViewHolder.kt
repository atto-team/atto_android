package com.nimontoy.android.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nimontoy.android.model.Data

/**
 * Created by leekijung on 2019. 4. 21..
 */

abstract class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindData(data: Data)
}

