package com.nimontoy.android.viewmodel.controller

import com.nimontoy.android.Variable
import com.nimontoy.android.model.Data
import com.nimontoy.android.viewmodel.BaseViewModel
import java.util.*

/**
 * Created by leekijung on 2019. 4. 21..
 */

open class DataListViewModel : BaseViewModel() {
    var dataList: MutableList<Data> = ArrayList()
    var dataListToAdd: Variable<MutableList<Data>> = Variable()

    open fun getDataList(startHandler: (() -> Unit)?,
                         successHandler: (() -> Unit)?,
                         failureHandler: (() -> Unit)?,
                         endHandler: (() -> Unit)?) {}

    protected fun onResponseWith(dataList: MutableList<Data>) {
        this addList dataList
    }

    protected infix fun add(Data: Data) {
        dataList.add(Data)
        changeData()
    }

    protected fun add(index: Int, Data: Data) {
        dataList.add(index, Data)
        changeData()
    }

    protected operator fun set(index: Int, Data: Data) {
        dataList[index] = Data
        changeData()
    }

    private infix fun addList(list: List<Data>) {
        dataList.addAll(list)
        changeData()
    }

    private fun clearList() {
        if (dataList.isEmpty()) return
        dataList.clear()
        changeData()
    }

    private fun changeData() {
        dataListToAdd.set(dataList)
    }

    fun refresh(endHandler: (() -> Unit)) {
        getDataList(null, { clearList() }, null, endHandler)
    }

    open fun clearHandler() {
        dataList.forEach {
            it.handler = { }
            it.detailHandler = { }
        }
    }

    fun clearDisposable() {
        compositeSubscription.clear()
    }
}
