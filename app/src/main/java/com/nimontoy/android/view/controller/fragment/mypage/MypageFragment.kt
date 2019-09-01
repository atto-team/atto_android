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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        viewModel = MyPageViewModel()

        dataRecyclerAdapter = DataRecyclerAdapter(viewModel.dataList)

        initViews(view)
        bindViews()
        initData()

        return view
    }
}