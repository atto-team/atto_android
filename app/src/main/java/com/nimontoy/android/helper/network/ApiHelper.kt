package com.nimontoy.android.helper.network

import io.reactivex.Flowable

/**
 * Created by leekijung on 2019. 7. 14..
 */

// TODO REST API에 따라 구현
object ApiHelper {

    fun getFeedList() : Flowable<String> {
        return NetworkHelper.apiSecureService.getResources("feeds")
    }

}

