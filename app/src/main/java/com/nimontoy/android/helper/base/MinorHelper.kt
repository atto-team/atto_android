package com.nimontoy.android.helper.base

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.nimontoy.android.AttoApplication
import com.nimontoy.android.BuildConfig
import com.crashlytics.android.Crashlytics
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by leekijung on 2019. 7. 9..
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

    fun toast(text: String = "", duration: Int = Toast.LENGTH_SHORT) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(AttoApplication.appContext, text, duration).show()
        }
    }

    fun logDebug(msg: String) {
        logDebug("", msg)
    }

    fun logDebug(tag: String?, msg: String?) {
        if (BuildConfig.DEBUG && tag != null && msg != null) {
            Log.d(tag, msg)
        }
    }

    fun logException(ex: Exception) {
        logException("", ex)
    }

    fun logException(tag: String, ex: Exception) {
        logDebug(tag, ex.toString())
        Crashlytics.logException(ex)
    }

    fun logException(tag: String, ex: Throwable) {
        logException(tag, Exception(ex))
    }
}