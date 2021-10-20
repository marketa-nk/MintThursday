package com.mintthursday.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String,
    val countPortion: Int,
    val time: Int,
    val category: String,
    val ingredients: List<Ingredient>,
    val steps: List<Step>,
    val link: String,
) : Parcelable