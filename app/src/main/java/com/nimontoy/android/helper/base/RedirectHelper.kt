package com.nimontoy.android.helper.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable

import java.util.ArrayList

import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.nimontoy.android.basic.Code
import com.nimontoy.android.view.controller.activity.main.MainActivity
import kotlin.reflect.KClass

object RedirectHelper {

    fun goToActivity(activity: Activity, clz: KClass<*>, extraMap: Map<String, Any>) {
        val intent = Intent(activity.applicationContext, clz.java)
        activity.startActivity(
            checkTypeAndPutIntent(
                intent,
                extraMap.entries
            )
        )
    }

    fun goToActivity(context: Context, intent: Intent) {
        context.startActivity(intent)
    }

    fun goToActivity(context: Context, clz: KClass<*>, extraMap: Map<String, Any> = mapOf()) {
        val intent = Intent(context, clz.java)
        if (context !is Activity) {
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(
            if(extraMap.isNotEmpty())
                checkTypeAndPutIntent(
                    intent,
                    extraMap.entries
                )
            else intent
        )
    }

    private fun checkTypeAndPutIntent(intent: Intent, entrySet: Set<Map.Entry<String, Any>>): Intent {
        for (entry in entrySet) {
            val key = entry.key
            val value = entry.value
            when (value.javaClass.name) {
                "java.lang.Integer" -> intent.putExtra(key, value as Int)
                "java.lang.String" -> intent.putExtra(key, value as String)
                "java.lang.Boolean" -> intent.putExtra(key, value as Boolean)
                "android.os.Bundle" -> intent.putExtra(key, value as Bundle)
                "java.util.ArrayList" -> intent.putParcelableArrayListExtra(key, value as ArrayList<out Parcelable>)
                else -> intent.putExtra(key, value as Parcelable)
            }
        }
        return intent
    }

    fun goToMain(context: Context) {
        goToActivity(context, MainActivity::class)
    }

    fun reloadRoot(activity: Activity, isLogout: Boolean = false) {
        val intent = activity.applicationContext.packageManager
            .getLaunchIntentForPackage(activity.applicationContext.packageName)
        intent?.let {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            if (!isLogout)
                intent.putExtra("isLogout", Code.LOGIN_SUCCESS.code)
            else
                intent.putExtra("isLogout", Code.LOGOUT_SUCCESS.code)
            goToActivity(
                activity.applicationContext,
                intent
            )
        }
        activity.finish()
    }
}
