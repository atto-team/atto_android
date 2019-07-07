package com.atto.android.helper

import com.atto.android.Variable
import com.atto.android.model.Data

/**
 * Created by leekijung on 2019. 4. 10..
 */

class ContentUpdateHelper {

    val updateVariable = Variable<Data>()

    fun setUpdateVariable(data: Data) {
        updateVariable.set(data)
    }

    companion object {
        private var instance: ContentUpdateHelper? = null

        fun getInstance(): ContentUpdateHelper {
            if (instance == null) instance = ContentUpdateHelper()
            return instance as ContentUpdateHelper
        }
    }
}
