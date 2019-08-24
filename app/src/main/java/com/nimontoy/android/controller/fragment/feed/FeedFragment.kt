package com.nimontoy.android.controller.fragment.feed

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nimontoy.android.R
import com.nimontoy.android.controller.fragment.DataListFragment
import com.nimontoy.android.view.custom.ImageCellView

class FeedFragment : DataListFragment() {

    lateinit var imageCellView : ImageCellView

    companion object {
        fun newInstance(): FeedFragment {
            return FeedFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        initViews(view)
        bindViews()
        initData()
        return view
    }

    override fun initViews(view: View) {
        imageCellView = view.findViewById(R.id.imageCellView)
    }

    override fun bindViews() {

        val images = ArrayList<Int> ()
        images.add(R.drawable.kakaoaccount_icon)
        images.add(R.drawable.kakaoaccount_icon)
        images.add(R.drawable.kakaoaccount_icon)
        images.add(R.drawable.kakaoaccount_icon)
        images.add(R.drawable.kakaoaccount_icon)
        images.add(R.drawable.kakaoaccount_icon)


        imageCellView.setImage(images)
    }

    override fun initData() {
    }

    override fun onDetach() {
        super.onDetach()
    }

}