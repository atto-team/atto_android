package com.nimontoy.android.viewmodel.feed

import android.annotation.SuppressLint
import com.nimontoy.android.helper.ResponseHelper
import com.nimontoy.android.helper.network.ApiHelper
import com.nimontoy.android.model.ResponseData
import com.nimontoy.android.viewmodel.DataListViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class FeedListViewModel : DataListViewModel() {

    override fun getDataList(
        startHandler: (() -> Unit)?,
        successHandler: (() -> Unit)?,
        failureHandler: (() -> Unit)?,
        endHandler: (() -> Unit)?
    ) {
        getFeedList().subscribe ({
            onResponseWith(it.dataList)
        }) {

        }

        // TODO 각 getDataList() 호출에 대한 로직 구현 할 것
        // 필요하면 startHandler에는 로딩이라던지, success가되면 dismiss를 한다던지, error의 경우에는 Alert를 구현한다던지..
    }


    private fun getFeedList(): Flowable<ResponseData> {
        return ApiHelper.getFeedList()
            .onBackpressureBuffer()
            .map { ResponseHelper.makeDataList(it) }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

}