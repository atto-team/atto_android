package com.atto.android.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil


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

    override fun equals(obj: Any?): Boolean {
        if (obj === this)
            return true

        val data = obj as Data

        return data.id === this.id
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

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Data> = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(@NonNull oldItem: Data, @NonNull newItem: Data): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(@NonNull oldItem: Data, @NonNull newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }
}
