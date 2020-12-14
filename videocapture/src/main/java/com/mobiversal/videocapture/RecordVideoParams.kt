package com.mobiversal.videocapture

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Biro Csaba on 14/12/2020.
 */
data class RecordVideoParams(
    val description: String,
    val minVideoLengthMillis: Long,
    val minVideoErrorTitle: String,
    val minVideoErrorDescription: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString() ?: "",
        source.readLong(),
        source.readString() ?: "",
        source.readString() ?: ""
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(description)
        writeLong(minVideoLengthMillis)
        writeString(minVideoErrorTitle)
        writeString(minVideoErrorDescription)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RecordVideoParams> =
            object : Parcelable.Creator<RecordVideoParams> {
                override fun createFromParcel(source: Parcel): RecordVideoParams =
                    RecordVideoParams(source)

                override fun newArray(size: Int): Array<RecordVideoParams?> = arrayOfNulls(size)
            }
    }
}