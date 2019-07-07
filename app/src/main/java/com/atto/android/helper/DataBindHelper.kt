package com.atto.android.helper

import android.content.Context
import com.atto.android.model.Data
import com.atto.android.type.Type
import com.atto.android.viewmodel.ViewModel

/**
 * Created by leekijung on 2019. 4. 21..
 */

class DataBindHelper {

    fun bindList(list: List<Data>, context: Context, viewModel: ViewModel) {
        for (data in list) {
            bindData(data, context, viewModel)
        }
    }

    private fun bindData(data: Data, context: Context, viewModel: ViewModel) {
    }

    companion object {
        private var instance: DataBindHelper? = null

        fun getInstance(): DataBindHelper {
            if (instance == null) instance = DataBindHelper()
            return instance as DataBindHelper
        }
    }

}
