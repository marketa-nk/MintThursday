package com.mintthursday.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Ingredient(val name: String, val quantity: Double, val unit: String) : Parcelable