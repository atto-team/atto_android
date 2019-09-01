package com.nimontoy.android.mapper

import com.nimontoy.android.R
import com.nimontoy.android.basic.Type
import com.nimontoy.android.model.Data

/**
 * Created by leekijung on 2019. 4. 21..
 */

object DataLayoutMapper {

    fun map(data: Data): Int {
        return when (data.type) {
            Type.FEED_CELL -> R.layout.viewholder_feed
            else -> R.layout.viewholder_empty
        }
    }
}
