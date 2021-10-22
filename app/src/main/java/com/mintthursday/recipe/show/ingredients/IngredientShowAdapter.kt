package com.mintthursday.recipe.show.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mintthursday.databinding.ViewIngredientShowBinding
import com.mintthursday.models.IngredientChecked
import com.mintthursday.recipe.show.ingredients.IngredientShowAdapter.IngredientShowViewHolder

class IngredientShowAdapter(
    private var ingredientShowList: List<IngredientChecked>,
    private val onItemClick: OnItemCheckListener
) : RecyclerView.Adapter<IngredientShowViewHolder>() {

    interface OnItemCheckListener {
        fun onItemCheck(ingredientChecked: IngredientChecked, itemPosition: Int)
        fun onItemUncheck(ingredientChecked: IngredientChecked, itemPosition: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientShowViewHolder {

        val binding = ViewIngredientShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientShowViewHolder, position: Int) {
        holder.bind(ingredientShowList[position])
    }

    override fun getItemCount(): Int {
        return ingredientShowList.size
    }

    fun setItems(ingredients: List<IngredientChecked>) {
        ingredientShowList = ingredients
        notifyDataSetChanged()
    }

    inner class IngredientShowViewHolder(private val binding: ViewIngredientShowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredientChecked: IngredientChecked) {
            val ingredient = ingredientChecked.ingredient
            binding.ingrName.text = ingredient.name
            binding.ingrQty.text = (ingredient.quantity * ingredientChecked.count).toString()
            binding.ingrUnit.text = ingredient.unit
            binding.checkBox.isChecked = ingredientChecked.checked
            binding.checkBox.setOnClickListener {
                val checked = !binding.checkBox.isChecked
                binding.checkBox.isChecked = checked
                if (checked) {
                    onItemClick.onItemCheck(ingredientChecked, adapterPosition)
                } else {
                    onItemClick.onItemUncheck(ingredientChecked, adapterPosition)
                }
            }
        }
    }
}