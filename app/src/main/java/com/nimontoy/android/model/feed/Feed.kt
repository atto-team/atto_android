package com.nimontoy.android.model.feed

import android.os.Parcelable
import com.nimontoy.android.model.Data

data class Feed (
    override var type : String,
    var userName : String,
    var date : String,
    var time : String,
    var contents : String,
    var images : ArrayList<Int>
): Data(type), Parcelable