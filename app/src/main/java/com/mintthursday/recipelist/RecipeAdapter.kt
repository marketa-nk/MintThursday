package com.mintthursday.recipelist

import androidx.recyclerview.widget.RecyclerView
import com.mintthursday.recipelist.RecipeAdapter.RecipeViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.mintthursday.R
import android.view.View.OnLongClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.mintthursday.models.Recipe

class RecipeAdapter : RecyclerView.Adapter<RecipeViewHolder>() {
    private var items: MutableList<Recipe> = mutableListOf()
    private var recipeListener: OnRecipeClickListener? = null
    fun setOnItemClickListenerRecipe(itemClickListenerRecipe: OnRecipeClickListener?) {
        recipeListener = itemClickListenerRecipe
    }

    fun setItems(items: List<Recipe>) {
        this.items = items.toMutableList()
        notifyItemRangeInserted(0, items.size)
    }

    fun addItem(recipe: Recipe) {
        items.add(0, recipe)
        notifyItemInserted(items.indexOf(recipe))
    }

    fun clearItems() {
        items.clear()
        notifyItemRangeRemoved(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, OnLongClickListener {
        private val recipeImageView: ImageView
        private val recipeName: TextView
        fun bind(recipe: Recipe) {
            recipeName.text = recipe.name
            recipeImageView.setImageDrawable(AppCompatResources.getDrawable(recipeImageView.context, R.drawable.img_food))
        }

        override fun onClick(v: View) {
            if (recipeListener != null) {
                val recipe = items[adapterPosition]
                recipeListener!!.onItemClick(recipe, adapterPosition)
            }
        }

        override fun onLongClick(v: View): Boolean {
            if (recipeListener != null) {
                val recipe = items[adapterPosition]
                recipeListener!!.onItemLongClick(recipe, adapterPosition)
            }
            return true
        }

        init {
            recipeImageView = itemView.findViewById(R.id.recipe_image_view)
            recipeName = itemView.findViewById(R.id.recipe_name)
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
    }
}