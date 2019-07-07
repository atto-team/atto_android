package com.atto.android.helper

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by leekijung on 2019. 4. 21..
 */

object MinorHelper {
    private var gson: Gson? = null
    fun getGson(): Gson? {
        if (gson == null) {
            gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
        }
        return gson

    }
}