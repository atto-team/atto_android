package com.nimontoy.android.mapper

import com.nimontoy.android.R
import com.nimontoy.android.model.Data

/**
 * Created by leekijung on 2019. 4. 21..
 */

object DataLayoutMapper {

    fun map(data: Data): Int {
        if (data.type == "null" || data.type.isEmpty()) return R.layout.viewholder_empty
        when (data.type) {
            "header_cell" -> {
                return R.layout.viewholder_header
            }
            "profile_cell" -> {
                return R.layout.viewholder_profile
            }
            "space_cell" -> {
                return R.layout.viewholder_space
            }
            "event_card_cell" ->{
                return R.layout.viewholder_event_card
            }
        }
        return R.layout.viewholder_empty
    }
}
