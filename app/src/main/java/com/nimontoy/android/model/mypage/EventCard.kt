package com.nimontoy.android.model.mypage

import android.os.Parcelable
import com.nimontoy.android.model.Data
import kotlinx.android.parcel.Parcelize
import com.nimontoy.android.basic.Type

@Parcelize
data class EventCard(
    override var id: String = "null",
    override var type: Type = Type.EVENT_CARD_CELL,
    override var scheme: String = "null",
    var title: String = "null"
): Data(), Parcelable