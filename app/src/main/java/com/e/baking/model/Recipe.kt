package com.e.baking.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    var id: String? = null,
    var name: String? = null,
    var servings: String? = null,
    var image: String? = null,
    var ingredients: List<Ingredient>? = null,
    var steps: List<Step>? = null
) : Parcelable
