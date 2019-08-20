package com.nimontoy.android.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import kotlinx.android.synthetic.main.viewholder_user_name.*

/**
 * Created by leesujung on 2019. 8. 18..
 */
class UserNameViewHolder (itemView: View) : DataViewHolder(itemView) {

    val user_name: TextView = itemView.findViewById(R.id.user_name)

    override fun bindData(data: Data) {
        when (data.type) {

        }
    }
}