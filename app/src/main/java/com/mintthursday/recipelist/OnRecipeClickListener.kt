package com.mintthursday.recipelist

import com.mintthursday.models.Recipe

interface OnRecipeClickListener {
    fun onItemClick(recipe: Recipe, position: Int)
    fun onItemLongClick(recipe: Recipe, position: Int): Boolean
}