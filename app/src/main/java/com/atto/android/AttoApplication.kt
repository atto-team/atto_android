package com.atto.android

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

/**
 * Created by leekijung on 2019. 7. 9..
 */

class AttoApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Fabric.with(this, Crashlytics())
    }

    companion object {
        val TAG = "Atto App"
        lateinit var appContext: Context
            private set
    }
}
