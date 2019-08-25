package com.nimontoy.android.helper.session

import android.annotation.SuppressLint
import android.app.Activity
import android.provider.Settings
import android.util.Log

import com.crashlytics.android.Crashlytics
import com.google.gson.JsonParser
import com.nimontoy.android.AttoApplication
import com.nimontoy.android.helper.PreferencesHelper
import com.nimontoy.android.helper.RedirectHelper
import java.util.HashMap
import io.reactivex.Observable

/**
 * Created by leekijung on 2019. 8. 25..
 */

@SuppressLint("CheckResult")
class SessionHelper {

    internal enum class LoginType(var type: String) {
        KAKAO("kakao"), FACEBOOK("facebook"), TWITTER("twitter"), GOOGLE("google")
    }

    internal enum class Key(var value: String) {
        AccessToken("SessionHelper.accessToken"),
        IsLogin("SessionHelper.isLogin"),
        UserId("SessionHelper.userId"),
        UserName("SessionHelper.userName"),
        UserPhone("SessionHelper.userPhone"),
        UserEmail("SessionHelper.userEmail)")
    }

    companion object {
        private val TAG = "SessionHelper"

        @SuppressLint("HardwareIds")
        private val ANDROID_ID = Settings.Secure.getString(
            AttoApplication.appContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        private var accessToken: String? = null

        fun storeAccessToken(accessToken: String) {
            SessionHelper.accessToken = accessToken
            Crashlytics.log(Log.INFO, TAG, "엑세스 토큰 저장 : $accessToken")
            PreferencesHelper.store(Key.AccessToken.value, accessToken)
        }

        fun getAccessToken(): String? {
            accessToken = PreferencesHelper.getString(Key.AccessToken.value, "")
            Crashlytics.log(Log.INFO, TAG, "엑세스 토큰 가져옴 : " + accessToken!!)
            return accessToken
        }

        fun existAccessToken(): Boolean {
            return if (accessToken != null && !accessToken!!.isEmpty()) {
                true
            } else {
                PreferencesHelper.exist(Key.AccessToken.value)
            }
        }

        var userId: Int
            get() = PreferencesHelper.getInt(Key.UserId.value, -1)
            set(id) {
                PreferencesHelper.store(Key.UserId.value, id)
            }

        var userName: String
            get() = PreferencesHelper.getString(Key.UserName.value, "")
            set(name) {
                PreferencesHelper.store(Key.UserName.value, name)
            }

        var userPhone: String
            get() = PreferencesHelper.getString(Key.UserPhone.value, "")
            set(phone) {
                PreferencesHelper.store(Key.UserPhone.value, phone)
            }

        var userEmail: String
            get() = PreferencesHelper.getString(Key.UserEmail.value, "")
            set(email) {
                PreferencesHelper.store(Key.UserEmail.value, email)
            }

        fun setLogined() {
            PreferencesHelper.store(Key.IsLogin.value, true)
        }

        val isLogin: Boolean
            get() = PreferencesHelper.getBoolean(Key.IsLogin.value, false)

        fun logout(activity: Activity) {
            accessToken = null
            for (key in Key.values()) {
                PreferencesHelper.remove(key.value)
            }
            PreferencesHelper.store(Key.IsLogin.value, false)
            RedirectHelper.reloadRoot(activity, true)
        }

        fun loginEmail(activity: Activity, email: String, password: String) {
            Crashlytics.setUserEmail(email)
            val parameters = HashMap<String, Any>()
            val jsonParser = JsonParser()
            parameters["email"] = email
            parameters["password"] = password
            Observable.just(parameters)
                .subscribe { params ->

                }

        }

        fun checkLogin(): Boolean {
            return if (isLogin) {
                Crashlytics.log(Log.INFO, TAG, "로그인 됨")
                false
            } else {
                Crashlytics.log(Log.INFO, TAG, "로그인 안됨")
                true
            }
        }

    }
}