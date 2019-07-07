package com.atto.android.mapper

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.atto.android.helper.MinorHelper
import com.atto.android.model.Data

/**
 * Created by leekijung on 2019. 4. 21..
 */

class DataMapper {

    companion object {
        private var gson: Gson? = null
        private var type: String? = null

        fun map(json: JsonObject): Data {
            gson = MinorHelper.getGson()
            type = json.get("type").asString

            when (type) {
            }
            return Data("null", type ?:"null")
        }

        private fun convertJsonType(json: JsonObject, clazz: Class<*>): Data {
            return gson!!.fromJson(json.toString(), clazz as Class<Data>)
        }
    }
}
