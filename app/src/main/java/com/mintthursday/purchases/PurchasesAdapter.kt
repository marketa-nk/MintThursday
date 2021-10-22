package com.mintthursday.purchases

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mintthursday.R
import com.mintthursday.databinding.ViewIngredientPurchaseBinding
import com.mintthursday.models.Purchase

class PurchasesAdapter(
    private val purchaseListener: OnPurchaseClickListener,
    private val onItemCheckListener: OnItemCheckListener
) : ListAdapter<Any, RecyclerView.ViewHolder>(PurchaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_RECIPE_TITLE) {
            RecipeTitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_recipe_title, parent, false))
        } else {
            val binding = ViewIngredientPurchaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ProductViewHolder(binding, purchaseListener, onItemCheckListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_RECIPE_TITLE -> (holder as RecipeTitleViewHolder).bind(getItem(position) as String)
            TYPE_PRODUCT -> (holder as ProductViewHolder).bind(getItem(position) as Purchase)
            else -> throw IllegalStateException("Unexpected value: " + holder.itemViewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is String -> TYPE_RECIPE_TITLE
            is Purchase -> TYPE_PRODUCT
            else -> throw IllegalStateException("Unexpected value")
        }
    }

    internal inner class RecipeTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeTitle: TextView = itemView.findViewById(R.id.recipe_title)

        fun bind(recipe: String) {
            recipeTitle.text = recipe
        }
    }

    private class ProductViewHolder(
        private val binding: ViewIngredientPurchaseBinding,
        private val purchaseListener: OnPurchaseClickListener,
        private val onItemCheckListener: OnItemCheckListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(purchase: Purchase) {
            val ingredient = purchase.ingredient
            binding.ingrName.text = ingredient.name
            binding.ingrQty.text = ingredient.quantity.toString()
            binding.ingrUnit.text = ingredient.unit
            binding.remove.setOnClickListener {
                purchaseListener.onRemoveClick(purchase)
            }
            binding.checkBox.isChecked = purchase.checked
            if (purchase.checked) {
                binding.ingrName.setPaintFlags(binding.ingrName.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            } else {
                binding.ingrName.setPaintFlags(binding.ingrName.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
            }
            binding.checkBox.setOnClickListener {
                val checked = binding.checkBox.isChecked
                if (checked) {
                    val newPurchase = purchase.copy(checked = true)
                    binding.ingrName.setPaintFlags(binding.ingrName.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                    onItemCheckListener.onItemCheck(newPurchase)
                } else {
                    val newPurchase = purchase.copy(checked = false)
                    binding.ingrName.setPaintFlags(binding.ingrName.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                    onItemCheckListener.onItemUncheck(newPurchase)
                }
            }
        }
    }

    companion object {
        private const val TYPE_RECIPE_TITLE = 1
        private const val TYPE_PRODUCT = 2
    }

    interface OnPurchaseClickListener {
        fun onRemoveClick(purchase: Purchase)
    }

    interface OnItemCheckListener {
        fun onItemCheck(purchase: Purchase)
        fun onItemUncheck(purchase: Purchase)
    }
}