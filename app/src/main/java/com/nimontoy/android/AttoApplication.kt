package com.nimontoy.android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import com.kakao.auth.KakaoSDK
import com.nimontoy.android.helper.session.KakaoSDKAdapter

/**
 * Created by leekijung on 2019. 7. 9..
 */

class AttoApplication : MultiDexApplication() {

    private fun init() {
        appContext = null
        activity = null
    }

    override fun onCreate() {
        super.onCreate()
        init()
        appContext = applicationContext
        Fabric.with(this, Crashlytics())
        KakaoSDK.init(KakaoSDKAdapter())
    }

    override fun onTerminate() {
        super.onTerminate()
        init()
    }

    companion object {
        val TAG = "Atto App"

        @SuppressLint("StaticFieldLeak")
        var activity: Activity? = null
        var appContext: Context? = null
            private set
    }

}
