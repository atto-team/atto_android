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
    private val Toy1 by lazy { itemView.findViewById<ToysCardCellView>(R.id.toy1) }
    private val Toy2 by lazy { itemView.findViewById<ToysCardCellView>(R.id.toy2) }
    private val emptyText by lazy { itemView.findViewById<TextView>(R.id.empty_text) }

    override fun reset() {
    }

    override fun bindData(data: Data, viewModel: DataCellViewModel) {
        super.bindData(data, viewModel)
        if (data is Profile) {
            val profile = data

            Toy2.visibility = View.GONE
            Toy1.visibility = View.GONE
            emptyText.visibility = View.VISIBLE
            profile.toys?.let {
                when (it.size) {
                    0 -> {
                        Toy2.visibility = View.GONE
                        Toy1.visibility = View.GONE
                        emptyText.visibility = View.VISIBLE
                    }
                    1 -> {
                        Toy1.visibility = View.VISIBLE
                        Toy2.visibility = View.INVISIBLE
                        Toy1.setToy(it[0]?.image, it[0]?.name)
                        emptyText.visibility = View.GONE
                    }
                    else -> {
                        Toy1.visibility = View.VISIBLE
                        Toy2.visibility = View.VISIBLE
                        Toy1.setToy(it[0]?.image, it[0]?.name)
                        Toy2.setToy(it[1]?.image, it[1]?.name)
                        emptyText.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun bindViews(data: Data) {
    }
}