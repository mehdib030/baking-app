package com.e.baking.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Step(
    var id: String? = null,
    var shortDescription: String? = null,
    var description: String? = null,
    var videoURL: String? = null,
    var thumbnailURL: String? = null
) : Parcelable
