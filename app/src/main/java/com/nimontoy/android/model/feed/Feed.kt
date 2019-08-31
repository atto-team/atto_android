package com.nimontoy.android.model.feed

import android.os.Parcelable
import com.nimontoy.android.model.Data
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feed (
    override var id : String,
    override var type : String,
    override var scheme : String,
    var userName : String,
    var date : String,
    var time : String,
    var contents : String,
    var images : ArrayList<Int>
): Data(id, type, scheme), Parcelable {

}