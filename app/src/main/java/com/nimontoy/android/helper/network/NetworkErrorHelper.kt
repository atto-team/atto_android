package com.nimontoy.android.helper.network

import com.nimontoy.android.helper.MinorHelper
import com.nimontoy.android.model.network.NetworkError
import com.nimontoy.android.util.UnicodeUtil

import retrofit2.Response

/**
 * Created by leekijung on 2019. 1. 17..
 */

object NetworkErrorHelper {
    fun showErrorMessage(response: Response<*>) {
        try {
            val json = response.errorBody()?.string()?.let { UnicodeUtil.unicodeConvert(it) }
            val gson = MinorHelper.getGson()
            val error = gson?.fromJson<NetworkError>(json, NetworkError::class.java)
            error?.message?.let { MinorHelper.toast(it) }
        } catch (e: Exception) { MinorHelper.logException(e) }
    }

    fun showErrorMessage(response: okhttp3.Response) {
        try {
            val json = response.body()?.string()?.let { UnicodeUtil.unicodeConvert(it) }
            val gson = MinorHelper.getGson()
            val error = gson!!.fromJson<NetworkError>(json, NetworkError::class.java)
            error?.message?.let { MinorHelper.toast(it) }
        } catch (e: Exception) { MinorHelper.logException(e) }

    }

    fun getErrorMessage(response: Response<*>): String? {
        try {
            val json = response.errorBody()?.string()?.let { UnicodeUtil.unicodeConvert(it) }
            val gson = MinorHelper.getGson()
            val error = gson?.fromJson<NetworkError>(json, NetworkError::class.java)
            return error?.message ?: ""
        } catch (e: Exception) { MinorHelper.logException(e) }
        return null
    }
}
