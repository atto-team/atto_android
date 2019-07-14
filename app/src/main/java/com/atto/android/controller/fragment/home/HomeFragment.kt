package com.atto.android.controller.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atto.android.R
import com.atto.android.controller.fragment.DataListFragment

class HomeFragment : DataListFragment() {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        /*initViews(view)
        bindViews()
        initData()*/
        return view
    }

    override fun initViews(view: View) {
        super.initViews(view)
    }

    override fun bindViews() {
        super.bindViews()
    }

    override fun initData() {
        super.initData()
    }

}