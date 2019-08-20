package com.nimontoy.android.controller.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nimontoy.android.R
import com.nimontoy.android.adapter.DataRecyclerAdapter
import com.nimontoy.android.helper.DataBindHelper
import com.nimontoy.android.model.Data
import com.nimontoy.android.viewmodel.DataListViewModel

open class DataListFragment : BaseFragment() {

    protected lateinit var viewModel: DataListViewModel
    protected lateinit var dataRecyclerAdapter: DataRecyclerAdapter
    protected lateinit var recyclerView: RecyclerView
    protected var swipeRefreshLayout: SwipeRefreshLayout? = null

    object testData : Data("testid", "user_name_cell", "scheme")
    object testData2 : Data("testid", "profile_cell", "scheme")
    open fun initViews(view: View) {
        val testDataList : List<Data> = listOf(testData, testData2)
        dataRecyclerAdapter = DataRecyclerAdapter(testDataList)

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
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
