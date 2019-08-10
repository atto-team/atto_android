package com.nimontoy.android.model

/**
 * Created by leekijung on 2019. 4. 21..
 */

data class ResponseData(
    val data: Data = Data(),
    val dataList: MutableList<Data> = mutableListOf())
