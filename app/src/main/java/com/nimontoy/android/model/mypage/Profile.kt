package com.nimontoy.android.model.mypage

import android.os.Parcelable
import com.nimontoy.android.basic.Type
import com.nimontoy.android.model.Data
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(
    override var id: String = "null",
    override var type: Type = Type.PROFILE_CELL,
    override var scheme: String = "null",
    var userName: String = "null",
    var image: String ?= null,
    var follow: String = "000000",
    var follower: String = "000000",

    var activity: Int = 0,
    var like: Int = 0,
    var point: Int = 0,
    var toys: MutableList<Toy?>? = null
): Data(), Parcelable