package com.nimontoy.android.mapper

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nimontoy.android.basic.Type
import com.nimontoy.android.helper.base.MinorHelper
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.feed.Feed
import kotlin.reflect.KClass

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

            return when (type) {
                Type.FEED_CELL -> convertJsonType(json, Feed::class)
                else -> Data()
            }
        }

        private fun convertJsonType(json: JsonObject, clazz: KClass<Feed>): Data {
            return gson!!.fromJson(json.toString(), clazz.java as Class<out Data>)
        }
    }
}
