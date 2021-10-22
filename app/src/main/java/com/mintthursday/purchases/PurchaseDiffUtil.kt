package com.mintthursday.purchases

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.mintthursday.models.Purchase

class PurchaseDiffUtil : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is String && newItem is String -> oldItem == newItem
            oldItem is Purchase && newItem is Purchase -> oldItem.id == newItem.id
            else -> false
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is String && newItem is String -> oldItem == newItem
            oldItem is Purchase && newItem is Purchase -> oldItem == newItem
            else -> false
        }
    }
}