package com.e.baking.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    var quantity: String? = null,
    var measure: String? = null,
    var ingredient: String? = null
) : Parcelable
