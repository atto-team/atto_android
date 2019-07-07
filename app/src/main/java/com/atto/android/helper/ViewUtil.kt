package com.atto.android.helper

import android.os.Build
import android.util.TypedValue
import android.view.View
import com.atto.android.AttoApplication


/**
 * Created by leekijung on 2019. 4. 21..
 */

object ViewUtil {
    fun dpToPixel(dp: Int): Int {
        val resources = AttoApplication.appContext.getResources()
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.getDisplayMetrics()).toInt()
    }

    fun clearLightStatusBar(view: View) {
        var flags = view.systemUiVisibility
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        view.systemUiVisibility = flags
    }
}