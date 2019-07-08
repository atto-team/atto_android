package com.atto.android.viewmodel

import com.atto.android.Variable
import com.google.gson.JsonParser
import com.atto.android.helper.ContentUpdateHelper
import com.atto.android.mapper.DataMapper
import com.atto.android.model.Data

import java.util.ArrayList

/**
 * Created by leekijung on 2019. 4. 21..
 */

open class DataListViewModel : BaseViewModel() {
    var dataList: MutableList<Data> = ArrayList()
    var dataChanged: Variable<Boolean> = Variable(false)
    var dataListToAdd: Variable<MutableList<Data>> = Variable()

    open fun getDataList(startHandler: (() -> Unit)?,
                         successHandler: (() -> Unit)?,
                         failureHandler: (() -> Unit)?,
                         endHandler: (() -> Unit)?) {}

    protected fun onResponseWith(dataList: MutableList<Data>) {
        dataListToAdd.set(dataList)
        addList(dataList)
    }

    protected fun parseDataList(jsonString: String): List<Data> {
        val dataList = ArrayList<Data>()
        try {
            val json = JsonParser().parse(jsonString).asJsonObject
            if (json.get("data").isJsonArray) {
                for (element in json.get("data").asJsonArray) {
                    val jsonObject = element.asJsonObject
                    dataList.add(DataMapper.map(jsonObject))
                }
            } else {
                dataList.add(DataMapper.map(json.get("data").asJsonObject))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dataList
    }

    protected fun add(Data: Data) {
        dataList.add(Data)
        changeData()
    }

    protected fun add(index: Int, Data: Data) {
        dataList.add(index, Data)
        changeData()
    }

    protected operator fun set(index: Int, Data: Data) {
        dataList.set(index, Data)
        changeData()
    }

    private fun addList(list: List<Data>) {
        dataList.addAll(list)
        changeData()
    }

    private fun clearList() {
        if (dataList.isEmpty()) return
        dataList.clear()
        changeData()
    }

    private fun changeData() {
        dataChanged.set(true)
    }

    fun refresh(endHandler: (() -> Unit)) {
        getDataList(null, { clearList() }, null, endHandler)
    }

    fun clearHandler() {
        for (data in dataList) {
            data.handler = { }
            data.detailHandler = { }
        }
    }

    fun clearDisposable() {
        compositeSubscription.clear()
    }

    protected fun subscribeCellUpdate() {
        ContentUpdateHelper.getInstance().updateVariable.asObservable()
                .subscribe{ recvData ->
                    if (recvData != null) {
                        for (data in dataList) {
                            if (recvData::class == data::class && recvData.code == data.code) {
                                val position = dataList.indexOf(data)
                                dataList[position] = recvData
                                changeData()
                                dataListToAdd.set(listOf(recvData) as MutableList<Data>)
                                break
                            }
                        }
                    }
                }.add()
    }
}
