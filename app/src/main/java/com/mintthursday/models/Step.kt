package com.mintthursday.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(var stepInstruction: String) : Parcelable