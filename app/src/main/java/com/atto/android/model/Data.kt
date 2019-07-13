package com.atto.android.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by leekijung on 2019. 4. 21..
 */

typealias DataHandler = ((Data) -> Unit)

open class Data(open var id: String = "null",
                open var type: String = "null",
                open var scheme: String = "null",
                var handler: DataHandler = { },
                var detailHandler: DataHandler = { }
) : Parcelable {

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        type = parcel.readString()
        scheme = parcel.readString()
    }

    fun run() {
        handler.invoke(this)
    }

    fun runDetail() {
        detailHandler.invoke(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeString(scheme)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}
