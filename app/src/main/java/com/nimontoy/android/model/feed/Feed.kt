package com.nimontoy.android.model.feed

import android.os.Parcelable
import com.nimontoy.android.basic.Type
import com.nimontoy.android.model.Data
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feed(
    var userName: String = "null",
    var date: String = "null",
    var time: String = "null",
    var contents: String = "null",
    var images: ArrayList<Int>? = null
): Data(), Parcelable
