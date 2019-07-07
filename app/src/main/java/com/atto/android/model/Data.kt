package com.atto.android.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by leekijung on 2019. 4. 21..
 */

open class Data(open var code: String = "null",
                open var type: String = "null",
                var handler: ((Data) -> Unit) = { },
                var detailHandler: ((Data) -> Unit) = { }
) : Parcelable {

    constructor(parcel: Parcel) : this() {
        code = parcel.readString()
        type = parcel.readString()
    }

    interface Handler {
        fun run(data: Data)
    }

    fun run() {
        handler.invoke(this)
    }

    fun runDetail() {
        detailHandler.invoke(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(type)
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
