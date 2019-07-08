package com.atto.android.controller.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.atto.android.R
import com.atto.android.adapter.DataRecyclerAdapter
import com.atto.android.helper.DataBindHelper
import com.atto.android.viewmodel.DataListViewModel

open class DataListFragment : BaseFragment() {

    protected lateinit var viewModel: DataListViewModel
    protected lateinit var dataRecyclerAdapter: DataRecyclerAdapter
    protected lateinit var recyclerView: RecyclerView
    protected var swipeRefreshLayout: SwipeRefreshLayout? = null

    open fun initViews(view: View) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = dataRecyclerAdapter
        swipeRefreshLayout?.setColorSchemeResources(R.color.colorPrimary)
    }

    open fun bindViews() {
        viewModel.dataListToAdd.asObservable().subscribe { dataList ->
            if(dataList != null) {
                context?.let{ context -> DataBindHelper.getInstance().bindList(dataList, context, viewModel) }
            }
        }.add()

        viewModel.dataChanged.asObservable().subscribe { changed ->
            if(changed) {
                dataRecyclerAdapter.notifyDataSetChanged()
            }
        }.add()

        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.refresh { swipeRefreshLayout?.isRefreshing = false }
        }
    }

    protected fun initData() {
        viewModel.getDataList(null, null, null, null)
    }

    override fun onDetach() {
        viewModel.clearHandler()
        super.onDetach()
    }
}
