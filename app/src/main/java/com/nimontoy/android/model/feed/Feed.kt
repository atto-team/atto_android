package com.nimontoy.android.model.feed

import android.os.Parcelable
import com.nimontoy.android.basic.Type
import com.nimontoy.android.model.Data
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feed(
    override var id: String = "null",
    override var type: Type = Type.FEED_CELL,
    override var scheme: String = "null",
    var userName: String = "null",
    var date: String = "null",
    var time: String = "null",
    var contents: String = "null",
    var images: MutableList<String?>? = null
): Data(), Parcelable
