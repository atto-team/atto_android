package com.nimontoy.android.model.home

import android.os.Parcelable
import com.nimontoy.android.basic.Type
import com.nimontoy.android.model.Data
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Home (
    override var id: String = "null",
    override var type: Type = Type.HOME_CELL,
    override var scheme: String = "null",
    var toysLineup: MutableList<Toy> = mutableListOf()
): Data(), Parcelable
