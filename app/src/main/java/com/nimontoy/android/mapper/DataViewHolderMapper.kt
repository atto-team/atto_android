package com.nimontoy.android.mapper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nimontoy.android.R
import com.nimontoy.android.view.adapter.viewholder.*

/**
 * Created by leekijung on 2019. 4. 21..
 */

object DataViewHolderMapper {

    fun map(parent: ViewGroup, layoutId: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return when (layoutId) {
            R.layout.viewholder_feed -> FeedViewHolder(itemView)
            else -> EmptyViewHolder(itemView)
        }
    }
}
