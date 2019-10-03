package com.nimontoy.android.view.controller.fragment.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nimontoy.android.R
import com.nimontoy.android.view.adapter.DataRecyclerAdapter
import com.nimontoy.android.view.controller.fragment.DataListFragment
import com.nimontoy.android.viewmodel.controller.main.feed.FeedViewModel
import com.nimontoy.android.viewmodel.controller.main.shop.ShopViewModel

class ShopFragment : DataListFragment() {

    companion object {
        fun newInstance(): ShopFragment {
            return ShopFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        viewModel = ShopViewModel()

        dataRecyclerAdapter = DataRecyclerAdapter(viewModel.dataList)

        /*initViews(view)
        bindViews()
        initData()*/

        return view
    }

}