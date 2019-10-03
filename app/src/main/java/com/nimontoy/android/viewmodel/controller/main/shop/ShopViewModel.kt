package com.nimontoy.android.viewmodel.controller.main.shop

import com.nimontoy.android.basic.Type
import com.nimontoy.android.model.Data
import com.nimontoy.android.viewmodel.controller.DataListViewModel

class ShopViewModel : DataListViewModel() {

    // TODO 테스트 코드이므로 반드시 지울 것..
    open class testUserData(override var id: String = "null",
                            override var type: Type = Type.EMPTY_CELL,
                            override var scheme: String = "null",
                            open var user_name: String = "null") : Data()
    object testData : testUserData("1", Type.HEADER_CELL, "scheme", "TEST USER")
    object testData2 : Data("2", Type.PROFILE_CELL, "scheme")
    object testSpace : Data("3", Type.SPACE_CELL, "scheme")
    object testData3 : Data("4", Type.EVENT_CARD_CELL, "scheme")

    override fun getDataList(
        startHandler: (() -> Unit)?,
        successHandler: (() -> Unit)?,
        failureHandler: (() -> Unit)?,
        endHandler: (() -> Unit)?
    ) {
        onResponseWith(listOf(testData, testData2, testSpace, testData3) as MutableList<Data>)
    }


}