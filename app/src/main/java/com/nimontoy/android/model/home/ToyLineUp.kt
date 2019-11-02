package com.nimontoy.android.model.home

import kotlinx.android.parcel.Parcelize

@Parcelize
data class ToyLineUp(
    override var toyName: String,
    override var toyImage: String,
    var isOpen: Boolean = false,
    var openPercent: Int = 0,
    var myCoinInserted: Int = 0
): Toy(toyName, toyImage) {

}