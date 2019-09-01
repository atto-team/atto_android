package com.nimontoy.android.mapper

import com.nimontoy.android.basic.Type
import com.nimontoy.android.viewmodel.cell.DataCellViewModel
import com.nimontoy.android.viewmodel.cell.EmptyCellViewModel
import com.nimontoy.android.viewmodel.cell.feed.FeedCellViewModel

object DataCellViewModelMapper {
    fun map(type: Type) : DataCellViewModel {
        return when(type) {
            Type.FEED_CELL -> FeedCellViewModel()
            else -> EmptyCellViewModel()
        }
    }
}