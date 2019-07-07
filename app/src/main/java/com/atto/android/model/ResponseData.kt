package com.atto.android.model

/**
 * Created by leekijung on 2019. 4. 21..
 */

class ResponseData {
    lateinit var data: Data
    lateinit var dataList: MutableList<Data>

    constructor(dataList: MutableList<Data>) {
        this.dataList = dataList
    }

    constructor(data: Data) {
        this.data = data
    }

    constructor() {}
}
