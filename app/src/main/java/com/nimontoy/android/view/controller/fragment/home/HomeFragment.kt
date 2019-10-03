package com.nimontoy.android.view.controller.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nimontoy.android.R
import com.nimontoy.android.view.adapter.DataRecyclerAdapter
import com.nimontoy.android.view.controller.fragment.DataListFragment
import com.nimontoy.android.viewmodel.controller.main.home.HomeViewModel

class HomeFragment : DataListFragment() {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = HomeViewModel()

        dataRecyclerAdapter = DataRecyclerAdapter(viewModel.dataList)

        /*initViews(view)
        bindViews()
        initData()*/
        return view
    }

}