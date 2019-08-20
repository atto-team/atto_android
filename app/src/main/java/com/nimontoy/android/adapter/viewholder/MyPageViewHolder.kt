package com.nimontoy.android.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.nimontoy.android.R
import com.nimontoy.android.model.Data

/**
 * Created by leesujung on 2019. 8. 18..
 */
class UserNameViewHolder (itemView: View) : DataViewHolder(itemView) {

    val user_name: TextView = itemView.findViewById(R.id.user_name)

    override fun bindData(data: Data) {
        when (data.type) {
            "header_cell" -> user_name.text = "Hello, "+ "Atto" +"!"
            //"profile_cell" ->
        }
    }
}

class ProfileViewHolder (itemView: View) : DataViewHolder(itemView) {

    override fun bindData(data: Data) {}
}

class EventCardViewHolder (itemView: View) : DataViewHolder(itemView) {

    val title_event_card: TextView = itemView.findViewById(R.id.title_event_card)

    override fun bindData(data: Data) {
        when (data.type) {
            "event_card_cell" -> title_event_card.text = "My Toys"
            //"profile_cell" ->
        }
    }
}