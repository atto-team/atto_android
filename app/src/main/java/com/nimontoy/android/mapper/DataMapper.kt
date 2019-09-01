package com.nimontoy.android.mapper

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nimontoy.android.basic.Type
import com.nimontoy.android.helper.MinorHelper
import com.nimontoy.android.model.Data

/**
 * Created by leekijung on 2019. 4. 21..
 */

class DataMapper {

    companion object {
        private var gson: Gson? = null
        private var type: Type? = null

        fun map(json: JsonObject): Data {
            gson = MinorHelper.getGson()
            type = Type.valueOf(json.get("type").asString)

            when (type) {

            }
            return Data()
        }

        private fun convertJsonType(json: JsonObject, clazz: Class<*>): Data {
            return gson!!.fromJson(json.toString(), clazz as Class<Data>)
        }
    }
}
