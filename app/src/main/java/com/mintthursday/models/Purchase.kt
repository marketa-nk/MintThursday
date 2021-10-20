package com.mintthursday.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Purchase(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val idRecipe: Long,
    val countPortion: Double,
    @Embedded
    val ingredient: Ingredient,
    val checked: Boolean,
) : Parcelable
