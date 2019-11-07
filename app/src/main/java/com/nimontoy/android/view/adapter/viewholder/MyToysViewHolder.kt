package com.nimontoy.android.view.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.kakao.usermgmt.response.model.User
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.mypage.Profile
import com.nimontoy.android.view.custom.ToysCardCellView
import com.nimontoy.android.viewmodel.cell.DataCellViewModel

class MyToysViewHolder (itemView: View) : DataViewHolder(itemView) {
    private val toy1 by lazy { itemView.findViewById<ToysCardCellView>(R.id.toy1) }
    private val toy2 by lazy { itemView.findViewById<ToysCardCellView>(R.id.toy2) }
    private val emptyText by lazy { itemView.findViewById<TextView>(R.id.empty_text) }

    override fun reset() {
        //Todo 매번 리싸이클 시 뷰 초기화 부탁
    }

    override fun bindData(data: Data, viewModel: DataCellViewModel) {
        super.bindData(data, viewModel)
        if (data is Profile) {
            toy2.visibility = View.GONE
            toy1.visibility = View.GONE
            emptyText.visibility = View.VISIBLE
            data.toys?.let {
                when (it.size) {
                    0 -> {
                        toy2.visibility = View.GONE
                        toy1.visibility = View.GONE
                        emptyText.visibility = View.VISIBLE
                    }
                    1 -> {
                        toy1.visibility = View.VISIBLE
                        toy2.visibility = View.INVISIBLE
                        toy1.setToy(it[0]?.image, it[0]?.name)
                        emptyText.visibility = View.GONE
                    }
                    else -> {
                        toy1.visibility = View.VISIBLE
                        toy2.visibility = View.VISIBLE
                        toy1.setToy(it[0]?.image, it[0]?.name)
                        toy2.setToy(it[1]?.image, it[1]?.name)
                        emptyText.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun bindViews(data: Data) {
        //Todo 클릭 이벤트 작성
    }
}