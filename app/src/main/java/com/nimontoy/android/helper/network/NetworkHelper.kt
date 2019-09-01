package com.nimontoy.android.helper.network

import android.annotation.SuppressLint
import com.nimontoy.android.basic.BaseUrl
import com.nimontoy.android.helper.MinorHelper
import com.nimontoy.android.helper.MinorHelper.toast
import com.nimontoy.android.util.UnicodeUtil
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.nimontoy.android.BuildConfig
import io.reactivex.Flowable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

import java.net.UnknownHostException
import java.util.HashMap
import java.util.concurrent.TimeUnit

/**
 * Created by leekijung on 2019. 7. 14..
 */

@SuppressLint("CheckResult")
open class NetworkHelper {

    companion object {
        private val TAG = "NetworkHelper"

        private val serviceHostForSecurity =
            if (BuildConfig.DEBUG) BaseUrl.BASE_DEBUG_URL
            else BaseUrl.BASE_URL

        private val loggingInterceptor by lazy { HttpLoggingInterceptor() }
        private var headerInterceptor: Interceptor
        private var errorInterceptor: Interceptor

        init {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

            headerInterceptor = Interceptor { chain ->
                val request = chain.request()
                val builder = request.newBuilder()
                    .header("X-Atto-Version", "android/" + BuildConfig.VERSION_NAME)
                    .header("Accept", "application/vnd.atto.v1+json")
                    .header("Content-CellType", "text/plain; charset=utf-8")
                    .header("Connection", "close")
                    .header("Authorization", "Bearer ")
                    .method(request.method(), request.body())

                lateinit var response: okhttp3.Response
                try {
                    response = chain.proceed(builder.build())
                } catch (e: UnknownHostException) {
                    e.printStackTrace()
                    toast("네트워크가 끊겼습니다. 잠시만 기다려주세요.")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                response
            }

            // TODO 각 동작 별 기본 행동 처리
            errorInterceptor = Interceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                when (response.code()) {
                    204 -> {
                        MinorHelper.logDebug(TAG, response.message())
                        NetworkErrorHelper.showErrorMessage(response)
                    }
                    401 -> {
                        MinorHelper.logDebug(TAG, response.message())
                        MinorHelper.logDebug(TAG, "body : " + UnicodeUtil.unicodeConvert(response.body().toString()))
                        MinorHelper.logDebug(TAG, "value : " + response.code())
                    }
                    403 -> {
                        MinorHelper.logDebug(TAG, response.message())
                        NetworkErrorHelper.showErrorMessage(response)
                    }
                    404 -> {
                        MinorHelper.logDebug(TAG, response.message())
                        NetworkErrorHelper.showErrorMessage(response)
                    }
                    422 -> {
                        MinorHelper.logDebug(TAG, response.message())
                        NetworkErrorHelper.showErrorMessage(response)
                    }
                    500 -> {
                        MinorHelper.logDebug(TAG, response.message())
                        NetworkErrorHelper.showErrorMessage(response)
                    }
                    503 -> {
                        MinorHelper.logDebug(TAG, response.message())
                        toast("현재 서버에 문제가 있습니다.")
                    }
                    504 -> {
                        MinorHelper.logDebug(TAG, response.message())
                        toast("요청시간이 초과되었습니다.")
                    }
                }
                response
            }
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(errorInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
        private val secureRetrofit = Retrofit.Builder()
            .baseUrl(serviceHostForSecurity)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        var apiSecureService = secureRetrofit.create(ApiService::class.java)

        private val noErrorInterceptClient = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
        private val noErrorInterceptRetrofit = Retrofit.Builder()
            .baseUrl(serviceHostForSecurity)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(noErrorInterceptClient)
            .build()

        var noErrorInterceptService =
            noErrorInterceptRetrofit.create<NoErrorInterceptService>(NoErrorInterceptService::class.java)
    }

    interface NoErrorInterceptService {
        @FormUrlEncoded
        @POST("/{resources}")
        fun postResources(@Path("resources") resource: String, @FieldMap parameters: HashMap<String, Any>): Flowable<String>

        @POST("/{resources}/{target}")
        fun postResourcesTarget(@Path("resources") resource: String, @Path("target") target: String): Flowable<Response<Void>>

        @FormUrlEncoded
        @POST("/{resources}/{target}")
        fun postResourcesTarget(@Path("resources") resource: String, @Path("target") target: String, @FieldMap parameters: HashMap<String, Any>): Flowable<String>

        @FormUrlEncoded
        @POST("/{resources}/{target}/{action}")
        fun postResourcesTargetAction(@Path("resources") resource: String, @Path("target") target: String, @Path("action") action: String, @FieldMap parameters: HashMap<String, Any>): Flowable<String>
    }

    interface ApiService {
        @GET
        fun getUrl(@Url url: String): Flowable<String>

        @GET("/{resources}")
        fun getResources(
            @Path("resources") resource: String): Flowable<String>

        @GET("/{resources}")
        fun getResources(
            @Path("resources") resource: String,
            @QueryMap params: Map<String, Any>): Flowable<String>

        @GET("/{resources}/{target}")
        fun getResourcesTarget(
            @Path("resources") resource: String,
            @Path("target") target: String): Flowable<String>

        @GET("/{resources}/{target}")
        fun getResourcesTarget(
            @Path("resources") resource: String,
            @Path("target") target: String,
            @QueryMap params: Map<String, Any>): Flowable<String>

        @GET("/{resources}/{target}/{action}")
        fun getResourcesTargetAction(
            @Path("resources") resource: String,
            @Path("target") target: String,
            @Path("action") action: String): Flowable<String>

        @GET("/{resources}/{target}/{action}")
        fun getResourcesTargetAction(
            @Path("resources") resource: String,
            @Path("target") target: String,
            @Path("action") action: String,
            @QueryMap params: Map<String, Any>): Flowable<String>

        @POST
        fun postUrl(@Url url: String): Flowable<String>

        @POST("/{resources}")
        fun postResources(
            @Path("resources") resource: String): Flowable<String>

        @FormUrlEncoded
        @POST("/{resources}")
        fun postResources(
            @Path("resources") resource: String,
            @FieldMap parameters: HashMap<String, Any>): Flowable<String>

        @POST("/{resources}/{target}")
        fun postResourcesTarget(
            @Path("resources") resource: String,
            @Path("target") target: String): Flowable<Void>

        @FormUrlEncoded
        @POST("/{resources}/{target}")
        fun postResourcesTarget(
            @Path("resources") resource: String,
            @Path("target") target: String,
            @FieldMap parameters: HashMap<String, Any>): Flowable<String>

        @POST("/{resources}/{target}/{action}")
        fun postResourcesTargetAction(
            @Path("resources") resource: String,
            @Path("target") target: String,
            @Path("action") action: String): Flowable<String>

        @POST("/{resources}/{target}/{action}")
        fun postResourcesTargetActionNoResponse(
            @Path("resources") resource: String,
            @Path("target") target: String,
            @Path("action") action: String): Flowable<Response<Void>>

        @FormUrlEncoded
        @POST("/{resources}/{target}/{action}")
        fun postResourcesTargetAction(
            @Path("resources") resource: String,
            @Path("target") target: String,
            @Path("action") action: String,
            @FieldMap parameters: HashMap<String, Any>): Flowable<String>

        @PATCH
        fun patchUrl(@Url url: String): Flowable<String>

        @FormUrlEncoded
        @PATCH("/{resources}")
        fun patchResources(
            @Path("resources") resource: String,
            @FieldMap parameters: HashMap<String, Any>): Flowable<String>

        @FormUrlEncoded
        @PATCH("/{resources}/{target}")
        fun patchResourcesTarget(
            @Path("resources") resource: String,
            @Path("target") target: String,
            @FieldMap parameters: HashMap<String, Any>): Flowable<String>

        @FormUrlEncoded
        @PATCH("/{resources}/{target}/{action}")
        fun patchResourcesTargetAction(
            @Path("resources") resource: String,
            @Path("target") target: String,
            @Path("action") action: String,
            @FieldMap parameters: HashMap<String, Any>): Flowable<String>

        @DELETE
        fun deleteUrl(@Url url: String): Flowable<String>

        @DELETE
        fun deleteUrlNoResponse(@Url url: String): Flowable<Response<Void>>

        @DELETE("/{resources}")
        fun deleteResources(@Path("resources") resource: String): Flowable<String>

        @FormUrlEncoded
        @HTTP(method = "DELETE", path = "/{resources}", hasBody = true)
        fun deleteResources(
            @Path("resources") resource: String,
            @FieldMap parameters: HashMap<String, Any>): Flowable<String>

        @DELETE("/{resources}/{target}")
        fun deleteResourcesTarget(
            @Path("resources") resource: String,
            @Path("target") target: String): Flowable<String>

        @DELETE("/{resources}/{target}")
        fun deleteResourcesTargetNoResponse(
            @Path("resources") resource: String,
            @Path("target") target: String): Flowable<Response<Void>>

        @DELETE("/{resources}/{target}/{action}")
        fun deleteResourcesTargetAction(
            @Path("resources") resource: String,
            @Path("target") target: String,
            @Path("action") action: String): Flowable<String>

        @DELETE("/{resources}/{target}/{action}")
        fun deleteResourcesTargetActionNoResponse(
            @Path("resources") resource: String,
            @Path("target") target: String,
            @Path("action") action: String): Flowable<Response<Void>>

    }
}
