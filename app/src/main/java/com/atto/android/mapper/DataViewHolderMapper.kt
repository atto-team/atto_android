package com.atto.android.mapper

import android.view.LayoutInflater
import android.view.ViewGroup

import com.atto.android.adapter.viewholder.DataViewHolder
import com.atto.android.adapter.viewholder.EmptyViewHolder

/**
 * Created by leekijung on 2019. 4. 21..
 */

object DataViewHolderMapper {
    fun map(parent: ViewGroup, layoutId: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        when (layoutId) { }
        return EmptyViewHolder(itemView)
    }
}
