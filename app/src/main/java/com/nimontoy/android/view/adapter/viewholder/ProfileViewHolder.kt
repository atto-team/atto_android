package com.nimontoy.android.view.adapter.viewholder

import android.util.Log
import android.view.View
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.mypage.Profile
import com.nimontoy.android.view.custom.ProfileCellView
import com.nimontoy.android.viewmodel.cell.DataCellViewModel

class ProfileViewHolder (itemView: View) : DataViewHolder(itemView) {
    private val ProfileCellView by lazy { itemView.findViewById<ProfileCellView>(R.id.profile_cell) }
    override fun reset() {
    }

    override fun bindData(data: Data, viewModel: DataCellViewModel) {
        super.bindData(data, viewModel)
        if (data is Profile) {
            val profile = data
            ProfileCellView.setProfileText(profile.follow, profile.follower)
            profile.image?.let { ProfileCellView.setProfileImage(it) }
        }
        ProfileCellView.setQRcodeClickListener(View.OnClickListener{ Log.d("ProfileViewHolder", "QR코드 링크") })
    }

    override fun bindViews(data: Data) {
        //ProfileCellView.setQRcodeClickListener(View.OnClickListener{ Log.d("ProfileViewHolder", "QR코드 링크") })
    }
}