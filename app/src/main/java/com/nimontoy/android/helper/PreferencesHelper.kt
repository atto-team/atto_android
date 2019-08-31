package com.nimontoy.android.helper

import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.nimontoy.android.AttoApplication

/**
 * Created by leekijung on 2019. 8. 25..
 */

object PreferencesHelper {
    private val pref: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(AttoApplication.appContext) }

    internal fun remove(key: String) {
        val edit = pref.edit()
        edit.remove(key)
        edit.apply()
    }

    internal fun store(key: String, value: String) {
        val edit = pref.edit()
        edit.putString(key, value)
        edit.apply()
    }

    internal fun store(key: String, value: Boolean) {
        store(key, java.lang.Boolean.toString(value))
    }

    internal fun store(key: String, value: Int) {
        store(key, Integer.toString(value))
    }

    internal fun store(key: String, value: Float) {
        store(key, java.lang.Float.toString(value))
    }

    internal fun exist(key: String): Boolean {
        return pref.contains(key)
    }

    internal fun getString(key: String, defaultValue: String): String {
        val pref = pref
        return pref.getString(key, defaultValue) ?: ""
    }

    internal fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val value = getString(key, "")
        return if (value.isEmpty()) {
            defaultValue
        } else {
            try {
                java.lang.Boolean.parseBoolean(value)
            } catch (e: NumberFormatException) {
                defaultValue
            }

        }
    }

    internal fun getInt(key: String, defaultValue: Int): Int {
        val value = getString(key, "")
        return if (value.isEmpty()) {
            defaultValue
        } else {
            try {
                Integer.parseInt(value)
            } catch (e: NumberFormatException) {
                defaultValue
            }

        }
    }

    internal fun getFloat(key: String, defaultValue: Float): Float {
        val value = getString(key, "")
        return if (value.isEmpty()) {
            defaultValue
        } else {
            try {
                java.lang.Float.parseFloat(value)
            } catch (e: NumberFormatException) {
                defaultValue
            }

        }
    }


}
