package com.nimontoy.android.view.controller.fragment.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nimontoy.android.R
import com.nimontoy.android.basic.Type
import com.nimontoy.android.model.Data
import com.nimontoy.android.view.adapter.DataRecyclerAdapter
import com.nimontoy.android.view.controller.fragment.DataListFragment
import com.nimontoy.android.viewmodel.controller.mypage.MyPageViewModel

class MypageFragment : DataListFragment() {

    companion object {
        fun newInstance(): MypageFragment {
            return MypageFragment()
        }
    }

    //test by leesujeong on 2019. 8. 20..
    open class testUserData(override var id: String = "null",
                            override var type: Type = Type.EMPTY_CELL,
                            override var scheme: String = "null",
                            open var user_name: String = "null") : Data()
    object testData : testUserData("1", Type.HEADER_CELL, "scheme", "TEST USER")
    object testData2 : Data("2", Type.PROFILE_CELL, "scheme")
    object testSpace : Data("3", Type.SPACE_CELL, "scheme")
    object testData3 : Data("4", Type.EVENT_CARD_CELL, "scheme")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_data_list, container, false)

        viewModel = MyPageViewModel()

        val testDataList : List<Data> = listOf(testData, testData2, testSpace, testData3)

        dataRecyclerAdapter = DataRecyclerAdapter(viewModel.dataList)


        viewModel.dataListToAdd.set(viewModel.dataList.apply {
            addAll(testDataList)
        })

        initViews(view)
        bindViews()
        initData()
        return view
    }
}