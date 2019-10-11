package com.nimontoy.android.model.mypage

import android.os.Parcelable
import com.nimontoy.android.model.Data
import kotlinx.android.parcel.Parcelize
import com.nimontoy.android.basic.Type

@Parcelize
data class Toy(
    override var id: String = "null",
    override var scheme: String = "null",
    var image: String ?= null,
    var name: String ?= null
): Data(), Parcelable