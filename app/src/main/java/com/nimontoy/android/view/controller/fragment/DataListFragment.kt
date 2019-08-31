package com.nimontoy.android.view.controller.fragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nimontoy.android.R
import com.nimontoy.android.view.adapter.DataRecyclerAdapter
import com.nimontoy.android.helper.DataBindHelper
import com.nimontoy.android.viewmodel.controller.DataListViewModel

open class DataListFragment : BaseFragment() {

    protected lateinit var viewModel: DataListViewModel
    protected lateinit var dataRecyclerAdapter: DataRecyclerAdapter
    protected lateinit var recyclerView: RecyclerView
    protected var swipeRefreshLayout: SwipeRefreshLayout? = null

    open fun initViews(view: View) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = dataRecyclerAdapter
        swipeRefreshLayout?.setColorSchemeResources(R.color.colorPrimary)
    }

    open fun bindViews() {
        viewModel.dataListToAdd.asObservable().subscribe { dataList ->
            if(dataList != null) {
                dataRecyclerAdapter.submitList(dataList)
                context?.let{ DataBindHelper.getInstance().bindList(dataList, it, viewModel) }
            }
        }.add()

        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.refresh { swipeRefreshLayout?.isRefreshing = false }
        }
    }

    open fun initData() {
        viewModel.getDataList(null, null, null, null)
    }

    override fun onDetach() {
        viewModel.clearHandler()
        super.onDetach()
    }
}
