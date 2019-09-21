package com.nimontoy.android.viewmodel.controller.feed

import android.annotation.SuppressLint
import com.nimontoy.android.helper.base.MinorHelper
import com.nimontoy.android.helper.network.ResponseHelper
import com.nimontoy.android.helper.network.ApiHelper
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.ResponseData
import com.nimontoy.android.model.feed.Feed
import com.nimontoy.android.viewmodel.controller.DataListViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class FeedViewModel : DataListViewModel() {

    override fun getDataList(
        startHandler: (() -> Unit)?,
        successHandler: (() -> Unit)?,
        failureHandler: (() -> Unit)?,
        endHandler: (() -> Unit)?
    ) {
        // TODO 테스트 이후 코드는 제거해 둘 것..


        // TODO 각 getDataList() 호출에 대한 로직 구현 할 것
        // 필요하면 startHandler에는 로딩이라던지, success가되면 dismiss를 한다던지, error의 경우에는 Alert를 구현한다던지..

        getTestFeedList().subscribe({
            onResponseWith(it.dataList)
        }, {
            it.message?.let { it1 -> MinorHelper.toast(it1) }
        })
    }

    private fun getTestFeedList(): Flowable<ResponseData> {
        val responseData = ResponseData(dataList = mutableListOf<Data>()
            .apply {
                add(Feed(images = mutableListOf<String?>().apply {
                    add("https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E")
                }))
                add(Feed(images = mutableListOf<String?>().apply {
                    add("https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E")
                    add("https://taegon.kim/wp-content/uploads/2018/05/image-5.png")
                }))
                add(Feed(images = mutableListOf<String?>().apply {
                    add("https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E")
                    add("https://taegon.kim/wp-content/uploads/2018/05/image-5.png")
                    add("https://helpx.adobe.com/content/dam/help/ko/photoshop/how-to/compositing/_jcr_content/main-pars/image/compositing_1408x792.jpg")
                }))
                add(Feed(images = mutableListOf<String?>().apply {
                    add("https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E")
                    add("https://taegon.kim/wp-content/uploads/2018/05/image-5.png")
                    add("https://helpx.adobe.com/content/dam/help/ko/photoshop/how-to/compositing/_jcr_content/main-pars/image/compositing_1408x792.jpg")
                    add("https://taegon.kim/wp-content/uploads/2018/05/image-5.png")
                }))
            })

        return Flowable.just(responseData)
    }

    private fun getFeedList(): Flowable<ResponseData> {
        return ApiHelper.getFeedList()
            .onBackpressureBuffer()
            .map { ResponseHelper.makeDataList(it) }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

}