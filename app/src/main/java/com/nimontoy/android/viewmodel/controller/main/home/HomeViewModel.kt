package com.nimontoy.android.viewmodel.controller.main.home

import android.annotation.SuppressLint
import com.nimontoy.android.helper.base.MinorHelper
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.ResponseData
import com.nimontoy.android.model.home.Home
import com.nimontoy.android.model.home.ToyLineUp
import com.nimontoy.android.viewmodel.controller.DataListViewModel
import io.reactivex.Flowable

class HomeViewModel : DataListViewModel() {

    @SuppressLint("CheckResult")
    override fun getDataList(
        startHandler: (() -> Unit)?,
        successHandler: (() -> Unit)?,
        failureHandler: (() -> Unit)?,
        endHandler: (() -> Unit)?
    ) {
        getTestToyList().subscribe({
            onResponseWith(it.dataList)
        }, {
            it.message?.let { it1 -> MinorHelper.toast(it1) }
        })
    }

    private fun getTestToyList(): Flowable<ResponseData> {
        val responseData = ResponseData(dataList = mutableListOf<Data>()
            .apply {
                add(Home(toysLineup = mutableListOf(ToyLineUp(toyName = "1", toyImage = "file:///android_asset/gogeta_ssgss.png"),
                    ToyLineUp(toyName = "2", toyImage = "file:///android_asset/broly_ss.png"),
                    ToyLineUp(toyName = "3", toyImage = "file:///android_asset/goku_black_rose.png"),
                    ToyLineUp(toyName = "4", toyImage = "file:///android_asset/goku_ultra_instinct_sign.png"),
                    ToyLineUp(toyName = "5", toyImage = "file:///android_asset/vegeta_ssgss.png")
                )))
            })
        return Flowable.just(responseData)
    }
}