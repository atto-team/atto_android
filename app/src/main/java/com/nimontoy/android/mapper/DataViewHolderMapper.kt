package com.nimontoy.android.mapper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nimontoy.android.R

import com.nimontoy.android.adapter.viewholder.DataViewHolder
import com.nimontoy.android.adapter.viewholder.EmptyViewHolder
import com.nimontoy.android.adapter.viewholder.ProfileViewHolder
import com.nimontoy.android.adapter.viewholder.UserNameViewHolder

/**
 * Created by leekijung on 2019. 4. 21..
 */

object DataViewHolderMapper {
    fun map(parent: ViewGroup, layoutId: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        when (layoutId) {
            R.layout.viewholder_user_name -> {  //user_name_cell
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_user_name, parent, false)
                return UserNameViewHolder(view)
            }
            R.layout.viewholder_profile -> { //profile_cell
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_profile, parent, false)
                return ProfileViewHolder(view)
            }
        }
        return EmptyViewHolder(itemView)
    }
}
