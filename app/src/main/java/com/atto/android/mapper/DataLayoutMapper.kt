package com.atto.android.mapper

import com.atto.android.R
import com.atto.android.model.Data

/**
 * Created by leekijung on 2019. 4. 21..
 */

object DataLayoutMapper {

    fun map(data: Data): Int {
        if (data.type == "null" || data.type.isEmpty()) return R.layout.viewholder_empty
        when (data.type) { }
        return R.layout.viewholder_empty
    }
}
