package com.mobiversal.videocapture

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Biro Csaba on 14/12/2020.
 */
@Parcelize
data class RecordVideoParams(
    val description: String,
    val minVideoLengthMillis: Long,
    val maxVideoLengthMillis: Long,
    val minVideoErrorTitle: String,
    val minVideoErrorDescription: String,
    val minVideoErrorPositiveButton: String,
    val minCameraBlockedErrorTitle: String,
    val minCameraBlockedErrorDescription: String,
    val minCameraBlockedPositiveButton: String
) : Parcelable