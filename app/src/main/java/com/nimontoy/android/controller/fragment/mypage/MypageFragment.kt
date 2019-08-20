package com.nimontoy.android.controller.fragment.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nimontoy.android.R
import com.nimontoy.android.controller.fragment.DataListFragment
import kotlinx.android.synthetic.main.activity_data_list.view.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*

class MypageFragment : DataListFragment() {

    companion object {
        fun newInstance(): MypageFragment {
            return MypageFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_data_list, container, false)
        initViews(view)
        //bindViews()
        //initData()
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