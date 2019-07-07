package com.atto.android

import android.content.Context
import android.support.multidex.MultiDexApplication

/**
 * Created by leekijung on 2019. 4. 21..
 */

class AttoApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        val TAG = "Atto App"
        lateinit var appContext: Context
            private set
    }
}
