package com.mintthursday.recipe.show.ingredients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mintthursday.R
import com.mintthursday.models.Ingredient
import com.mintthursday.recipe.show.ingredients.IngredientShowAdapter.IngredientShowViewHolder

class IngredientShowAdapter(
        private var ingredientShowList: List<Ingredient>,
        private val onItemClick: OnItemCheckListener
) : RecyclerView.Adapter<IngredientShowViewHolder>() {
    interface OnItemCheckListener {
        fun onItemCheck(ingredient: Ingredient)
        fun onItemUncheck(ingredient: Ingredient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_ingredient_show, parent, false)
        return IngredientShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientShowViewHolder, position: Int) {
        holder.bind(ingredientShowList[position])
    }

    override fun getItemCount(): Int {
        return ingredientShowList.size
    }

    fun setItems(ingredients: List<Ingredient>) {
        ingredientShowList = ingredients
        notifyDataSetChanged()
    }

    inner class IngredientShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox
        private val ingrName: TextView
        private val ingrQty: TextView
        private val ingrUnit: TextView
        fun bind(ingredient: Ingredient) {
            ingrName.text = ingredient.name
            ingrQty.text = ingredient.quantity.toString()
            ingrUnit.text = ingredient.unit
            checkBox.isClickable = false
            itemView.setOnClickListener {
                val checked = !checkBox.isChecked
                checkBox.isChecked = checked
                if (checked) {
                    onItemClick.onItemCheck(ingredient)
                } else {
                    onItemClick.onItemUncheck(ingredient)
                }
            }
        }

        init {
            ingrName = itemView.findViewById(R.id.ingr_name)
            ingrQty = itemView.findViewById(R.id.ingr_qty)
            ingrUnit = itemView.findViewById(R.id.ingr_unit)
            checkBox = itemView.findViewById(R.id.checkBox)
        }
    }
}