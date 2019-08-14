package com.nimontoy.android.adapter

import android.app.Activity
import android.app.Application
import android.content.Context
import com.kakao.auth.*
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

object GlobalApplication: Application() {
    @Volatile private var obj: GlobalApplication? = null
    @Volatile private var currentActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()
        obj = this
        KakaoSDK.init(KakaoSDKAdapter())

        // start koin
        //startKoin(this, appModule, logger = EmptyLogger())
    }


   fun getGlobalApplicationContext(): GlobalApplication?{
        return obj;
    }

    fun getCurrentActivity(): Activity? {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해야 한다.
    fun setCurrentActivity(currentActivity: Activity) {
        this.currentActivity = currentActivity;
    }

}

