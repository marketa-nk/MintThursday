package com.mintthursday.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientChecked(
        val ingredient: Ingredient,
        val count: Double,
        val checked: Boolean
) : Parcelable