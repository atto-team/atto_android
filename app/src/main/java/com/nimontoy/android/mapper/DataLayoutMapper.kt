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
            "user_name_cell" -> {
                return R.layout.viewholder_user_name
            }
            "profile_cell" -> {
                return R.layout.viewholder_profile
            }
        }
        return R.layout.viewholder_empty
    }
}
