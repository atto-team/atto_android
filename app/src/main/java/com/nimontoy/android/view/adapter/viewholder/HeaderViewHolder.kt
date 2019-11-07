package com.nimontoy.android.view.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.mypage.Profile
import com.nimontoy.android.viewmodel.cell.DataCellViewModel

/**
 * Created by leesujung on 2019. 8. 18..
 */
class HeaderViewHolder (itemView: View) : DataViewHolder(itemView) {
    private val headerText by lazy { itemView.findViewById<TextView>(R.id.header_text) }

    override fun reset() {
        headerText.setText("Hello, Ghost User!")
    }

    override fun bindData(data: Data, viewModel: DataCellViewModel) {
        super.bindData(data, viewModel)
        if (data is Profile) {
            headerText.setText("Hello, "+data.userName+"!")
        }
    }

    override fun bindViews(data: Data) {
        //Todo 클릭 이벤트 작성
    }

}