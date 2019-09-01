package com.nimontoy.android.mapper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nimontoy.android.view.adapter.viewholder.*

/**
 * Created by leekijung on 2019. 4. 21..
 */

object DataViewHolderMapper {

    //TODO 셀에 대한 구현은 각자 작업해둔 브랜치에서 해둘 것
    fun map(parent: ViewGroup, layoutId: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        when (layoutId) {

        }
        return EmptyViewHolder(itemView)
    }
}
