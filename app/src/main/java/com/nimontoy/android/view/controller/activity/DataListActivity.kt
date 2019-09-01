package com.nimontoy.android.view.controller.activity

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nimontoy.android.R
import com.nimontoy.android.view.adapter.DataRecyclerAdapter
import com.nimontoy.android.helper.base.DataBindHelper
import com.nimontoy.android.viewmodel.controller.DataListViewModel

@SuppressLint("CheckResult", "Registered")
open class DataListActivity : BaseActivity() {

    protected lateinit var viewModel: DataListViewModel
    protected lateinit var dataRecyclerAdapter: DataRecyclerAdapter
    protected lateinit var recyclerView: RecyclerView
    protected var swipeRefreshLayout: SwipeRefreshLayout? = null

    open fun initViews() {
        recyclerView = findViewById(R.id.recycler_view)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        recyclerView.adapter = dataRecyclerAdapter
        swipeRefreshLayout?.setColorSchemeResources(R.color.colorPrimary)
    }

    open fun bindViews() {
        viewModel.dataListToAdd.asObservable().subscribe {
            if(it != null) {
                dataRecyclerAdapter.submitList(it)
                DataBindHelper.getInstance().bindList(it, this, viewModel)
            }
        }.add()

        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.refresh { swipeRefreshLayout?.isRefreshing = false }
        }
    }

    open fun initData() {
        viewModel.getDataList(null, null, null, null)
    }

    override fun onDestroy() {
        viewModel.clearDisposable()
        viewModel.clearHandler()
        super.onDestroy()
    }

}
