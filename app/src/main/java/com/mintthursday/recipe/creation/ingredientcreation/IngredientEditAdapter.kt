package com.mintthursday.recipe.creation.ingredientcreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mintthursday.R
import com.mintthursday.models.Ingredient
import com.mintthursday.recipe.creation.ingredientcreation.IngredientEditAdapter.IngredientEditViewHolder

class IngredientEditAdapter : RecyclerView.Adapter<IngredientEditViewHolder>() {

    private lateinit var ingredientListener: OnIngredientClickListener
    var items = mutableListOf<Ingredient>()
        private set

    fun setItems(items: List<Ingredient>) {
        this.items = items.toMutableList()
        notifyItemRangeInserted(0, items.size)
    }

    fun setOnItemClickListener(itemClickListener: OnIngredientClickListener) {
        ingredientListener = itemClickListener
    }

    fun addItem(ingredient: Ingredient) {
        items.add(ingredient)
        notifyItemInserted(items.indexOf(ingredient))
    }

    private fun deleteItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateItem(ingredient: Ingredient, itemPosition: Int) {
        items[itemPosition] = ingredient
        notifyItemChanged(itemPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientEditViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.view_ingredient_edit,
                parent,
                false
            )
        return IngredientEditViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientEditViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class IngredientEditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val ingrName: TextView = itemView.findViewById(R.id.ingr_name)
        private val ingrQty: TextView = itemView.findViewById(R.id.ingr_qty)
        private val ingrUnit: TextView = itemView.findViewById(R.id.ingr_unit)
        private val btnIngrRemove: ImageButton = itemView.findViewById(R.id.ingr_remove)

        fun bind(ingredient: Ingredient) {
            ingrName.text = ingredient.name
            ingrQty.text = ingredient.quantity.toString()
            ingrUnit.text = ingredient.unit
        }

        override fun onClick(v: View) {
            if (v == btnIngrRemove) {
                deleteItem(adapterPosition)
            } else {
                val ingredient = items[adapterPosition]
                ingredientListener.onItemClick(ingredient, adapterPosition)
            }
        }

        init {
            btnIngrRemove.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }
    }

    interface OnIngredientClickListener {
        fun onItemClick(ingredient: Ingredient, itemPosition: Int)
    }
}