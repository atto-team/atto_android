package com.atto.android.controller.fragment.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atto.android.R
import com.atto.android.controller.fragment.DataListFragment

class NotificationFragment : DataListFragment() {

    companion object {
        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
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