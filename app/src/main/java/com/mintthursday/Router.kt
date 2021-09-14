package com.mintthursday

import com.mintthursday.models.Ingredient
import com.mintthursday.models.Recipe

interface Router {
    fun showRecipe(recipe: Recipe?)
    fun createNewRecipe()
    fun editRecipe(recipe: Recipe?)
    fun showNewIngredient()
    fun editIngredient(ingredient: Ingredient?, itemPosition: Int)
}