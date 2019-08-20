package com.nimontoy.android.mapper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nimontoy.android.R
import com.nimontoy.android.adapter.viewholder.*

/**
 * Created by leekijung on 2019. 4. 21..
 */

object DataViewHolderMapper {
    fun map(parent: ViewGroup, layoutId: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        when (layoutId) {
            //test by leesujeong on 2019. 8. 20..
            R.layout.viewholder_header -> {  //R.layout.viewholder_user_name
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_header, parent, false)
                return UserNameViewHolder(view)
            }
            R.layout.viewholder_profile -> { //R.layout.viewholder_profile
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_profile, parent, false)
                return ProfileViewHolder(view)
            }
            R.layout.viewholder_event_card -> { //R.layout.viewholder_profile
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_event_card, parent, false)
                return EventCardViewHolder(view)
            }
            //end test
        }
        return EmptyViewHolder(itemView)
    }
}
