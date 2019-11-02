package com.nimontoy.android.model.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Toy(
    open var toyName: String = "null",
    open var toyImage: String = "null"
): Parcelable
