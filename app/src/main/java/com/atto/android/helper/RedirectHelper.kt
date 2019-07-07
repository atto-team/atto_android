package com.atto.android.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable

import java.util.ArrayList

import android.content.Intent.FLAG_ACTIVITY_NEW_TASK

object RedirectHelper {

    fun goToActivity(activity: Activity, clz: Class<*>, extraMap: Map<String, Any>) {
        val intent = Intent(activity.applicationContext, clz)
        activity.startActivity(checkTypeAndPutIntent(intent, extraMap.entries))
    }

    fun goToActivity(context: Context, intent: Intent) {
        context.startActivity(intent)
    }

    fun goToActivity(context: Context, clz: Class<*>, extraMap: Map<String, Any>) {
        val intent = Intent(context, clz)
        if (context !is Activity) {
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(checkTypeAndPutIntent(intent, extraMap.entries))
    }

    private fun checkTypeAndPutIntent(intent: Intent, entrySet: Set<Map.Entry<String, Any>>): Intent {
        for (entry in entrySet) {
            val key = entry.key
            val value = entry.value
            when (value.javaClass.getName()) {
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

}
