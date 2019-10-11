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
            Type.HEADER_CELL -> R.layout.viewholder_header
            Type.PROFILE_CELL -> R.layout.viewholder_profile
            Type.INTRO_CELL -> R.layout.viewholder_intro
            Type.SPACE_CELL -> R.layout.viewholder_space
            Type.EVENT_CARD_CELL -> R.layout.viewholder_event_card
            Type.MY_TOYS_CELL -> R.layout.viewholder_mytoys
            else -> R.layout.viewholder_empty
        }
    }
}
