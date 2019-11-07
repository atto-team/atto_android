package com.nimontoy.android.view.adapter.viewholder

import android.content.Intent
import android.util.Log
import android.view.View
import com.nimontoy.android.R
import com.nimontoy.android.helper.base.RedirectHelper.goToActivity
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.mypage.Profile
import com.nimontoy.android.view.controller.activity.FollowActivity
import com.nimontoy.android.view.controller.activity.main.MainActivity
import com.nimontoy.android.view.custom.ProfileCellView
import com.nimontoy.android.viewmodel.cell.DataCellViewModel
import java.security.AccessController.getContext

class ProfileViewHolder (itemView: View) : DataViewHolder(itemView) {
    private val ProfileCellView by lazy { itemView.findViewById<ProfileCellView>(R.id.profile_cell) }
    override fun reset() {
        //Todo 매번 리싸이클 시 뷰 초기화 부탁
    }

    override fun bindData(data: Data, viewModel: DataCellViewModel) {
        super.bindData(data, viewModel)
        if (data is Profile) {
            ProfileCellView.setProfileText(data.follow, data.follower)
            data.image?.let { ProfileCellView.setProfileImage(it) }
        }

        ProfileCellView.setQRcodeClickListener(View.OnClickListener{ Log.d("ProfileViewHolder", "QR코드 링크") })
        ProfileCellView.setFollowClickListener(View.OnClickListener{ goToActivity(itemView.context, FollowActivity::class)})
    }

    override fun bindViews(data: Data) {
        //Todo 클릭 이벤트 작성
        //ProfileCellView.setQRcodeClickListener(View.OnClickListener{ Log.d("ProfileViewHolder", "QR코드 링크") })
    }
}