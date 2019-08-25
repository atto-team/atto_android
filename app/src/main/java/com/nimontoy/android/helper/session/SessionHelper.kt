package com.nimontoy.android.helper.session

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.provider.Settings
import android.util.Log

import com.crashlytics.android.Crashlytics
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sungbak.sungdaeli.BuildConfig
import com.sungbak.sungdaeli.SungBakApplication
import com.sungbak.sungdaeli.model.Variable
import com.sungbak.sungdaeli.helper.network.ApiHelper
import com.sungbak.sungdaeli.helper.network.NetworkHelper
import com.sungbak.sungdaeli.helper.network.ResponseHelper
import com.sungbak.sungdaeli.model.network.Status
import com.sungbak.sungdaeli.value.Code
import com.sungbak.sungdaeli.view.activity.content.main.MainActivity

import java.util.HashMap
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class SessionHelper {

    internal enum class Key private constructor(var value: String) {
        AccessToken("SessionHelper.accessToken"),
        IsLogin("SessionHelper.isLogin"),
        UserId("SessionHelper.userId"),
        UserName("SessionHelper.userName"),
        UserPhone("SessionHelper.userPhone"),
        UserEmail("SessionHelper.userEmail)")
    }

    internal fun createDeepLink() {
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse("http://sungbak.com/urls/")) //딥링크(정보를 담음)
            .setDynamicLinkDomain("n2q7z.app.goo.gl")       //동적링크 도메인 - 콘솔에서 주어짐.
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder(BuildConfig.APPLICATION_ID).build()) //패키지명
            .buildDynamicLink()

        val dynamicLinkUri = dynamicLink.getUri()

        println(dynamicLinkUri.toString())

        val shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse("http://sungbak.com/urls/"))
            .setDynamicLinkDomain("n2q7z.app.goo.gl")
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder(BuildConfig.APPLICATION_ID).build()) //패키지명

            .buildShortDynamicLink()
            .addOnCompleteListener(this as Executor, { task ->
                if (task.isSuccessful()) {
                    val shortLink = task.getResult().getShortLink()
                    val flowchartLink = task.getResult().getPreviewLink()
                    Log.e(TAG, "딥링크 : $shortLink")
                } else {
                    Log.e(TAG, "딥링크 에러 처리 필요")
                }
            })

    }

    companion object {
        private val TAG = "SessionHelper"

        @SuppressLint("HardwareIds")
        private val ANDROID_ID = Settings.Secure.getString(
            SungBakApplication.getAppContext().getContentResolver(),
            Settings.Secure.ANDROID_ID
        )

        private var accessToken: String? = null


        internal val notiBadgeVariable = Variable(0)

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

        fun login(activity: Activity, email: String, password: String) {
            Crashlytics.setUserEmail(email)

            val parameters = HashMap<String, Any>()
            val jsonParser = JsonParser()
            parameters["email"] = email
            parameters["password"] = password
            Observable.just(parameters)
                .subscribe { params ->
                    ApiHelper.postLogin(params)
                        .lastElement()
                        .map({ s ->
                            val jsonObject = jsonParser.parse(s) as JsonObject
                            val jsonDataObject = jsonObject.get("data") as JsonObject
                            jsonDataObject.get("api_token").asString
                        })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ apiToken ->
                            Crashlytics.log(Log.INFO, TAG, "로그인 계정의 token : $apiToken")
                            SessionHelper.storeAccessToken(apiToken)
                            ApiHelper.getStatus()
                                .lastElement()
                                .subscribeOn(Schedulers.single())
                                .map(???({ ResponseHelper.makeData() }))
                            .map { responseData -> responseData.data as Status }
                            .subscribe({ status ->
                                SessionHelper.userId = status.user.userInfo.id
                                SessionHelper.userEmail = status.user.userInfo.email
                                SessionHelper.userName = status.user.userInfo.name
                                SessionHelper.userPhone = status.user.userInfo.phoneNumber
                                SessionHelper.setLogined()
                                activity.setResult(Code.LOGIN_SUCCESS.value)
                                activity.finish()
                            }, { throwable -> SessionHelper.logout(activity) })
                        }, { throwable -> })
                }

        }

        // 가장 기본적인 인증 모듈 -> 공용 네트워크에 독립적이게
        fun loginByDeviceId(activity: Activity) {
            val parameters = HashMap<String, Any>()
            val jsonParser = JsonParser()
            val postLoginObservable: Observable<String>?

            if (!isLogin) {
                Crashlytics.log(Log.DEBUG, TAG, "로그인 안되어있는경우")
                parameters["udid"] = ANDROID_ID
                postLoginObservable = ApiHelper.postLogin(parameters)
                if (postLoginObservable == null) return
                postLoginObservable!!
                    .lastElement()
                    .map { s ->
                        val jsonObject = jsonParser.parse(s) as JsonObject
                        val jsonDataObject = jsonObject.get("data") as JsonObject
                        jsonDataObject.get("api_token").asString
                    }
                    .delay(1500, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { apiToken ->
                        Crashlytics.log(Log.INFO, TAG, apiToken)
                        Crashlytics.setUserIdentifier(apiToken)
                        storeAccessToken(apiToken)
                        RedirectHelper.goToActivityWithFinish(activity, MainActivity::class.java)
                    }
            } else {
                if (getAccessToken() != null && getAccessToken() != "") {
                    Observable.just(getAccessToken()!!)
                        .delay(1500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.single())
                        .subscribe { s ->
                            Crashlytics.log(Log.INFO, TAG, getAccessToken())
                            Crashlytics.setUserIdentifier(getAccessToken())
                            RedirectHelper.goToActivityWithFinish(
                                activity,
                                MainActivity::class.java
                            )
                        }
                } else {
                    logout(activity)
                }
            }

        }

        fun checkLogin(): Boolean {
            if (!isLogin) {
                Crashlytics.log(Log.INFO, TAG, "로그인 안됨")
                return false
            } else {
                Crashlytics.log(Log.INFO, TAG, "로그인 됨")
                return true
            }
        }

        fun syncStatus(handler: NetworkHelper.CompletionHandler) {}
    }
}
