package com.nimontoy.android.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Created by leekijung on 2019. 4. 21..
 */

typealias DataHandler = ((Data) -> Unit)

@Parcelize
open class Data(open var id: String = "null",
                open var type: String = "null",
                open var scheme: String = "null"
) : Parcelable {

    @IgnoredOnParcel
    var handler: DataHandler = { }
    @IgnoredOnParcel
    var detailHandler: DataHandler = { }

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

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + scheme.hashCode()
        result = 31 * result + handler.hashCode()
        result = 31 * result + detailHandler.hashCode()
        return result
    }

    companion object {

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
