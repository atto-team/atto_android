package com.atto.android.helper

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.atto.android.mapper.DataMapper
import com.atto.android.model.Data
import com.atto.android.model.ResponseData
import java.util.ArrayList

/**
 * Created by leekijung on 2019. 4. 21..
 */

object ResponseHelper {

    fun makeDataList(s: String): ResponseData {
        val dataList = ArrayList<Data>()
        try {
            val jsonObject = JsonParser().parse(s).asJsonObject
            val jsonArray = jsonObject.get("data").asJsonArray
            for (element in jsonArray) {
                dataList.add(DataMapper.map(element.asJsonObject))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ResponseData(dataList)
    }

    fun makeData(s: String): ResponseData {
        var jsonObject = JsonObject()
        try {
            jsonObject = JsonParser().parse(s).asJsonObject.get("data").asJsonObject
        } catch (ignored: Exception) {
        }

        return ResponseData(DataMapper.map(jsonObject))
    }
}
