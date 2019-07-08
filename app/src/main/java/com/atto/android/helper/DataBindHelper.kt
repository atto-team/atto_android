package com.atto.android.helper

import android.content.Context
import com.atto.android.model.Data
import com.atto.android.viewmodel.BaseViewModel

/**
 * Created by leekijung on 2019. 4. 21..
 */

class DataBindHelper {

    fun bindList(list: List<Data>, context: Context, viewModel: BaseViewModel) {
        for (data in list) {
            bindData(data, context, viewModel)
        }
    }

    private fun bindData(data: Data, context: Context, viewModel: BaseViewModel) {
    }

    companion object {
        private var instance: DataBindHelper? = null

        fun getInstance(): DataBindHelper {
            if (instance == null) instance = DataBindHelper()
            return instance as DataBindHelper
        }
    }

}
