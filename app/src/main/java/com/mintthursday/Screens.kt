package com.mintthursday

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mintthursday.models.Ingredient
import com.mintthursday.models.Recipe
import com.mintthursday.recipe.creation.NewRecipeFragment
import com.mintthursday.recipe.creation.ingredientcreation.IngredientFragment
import com.mintthursday.recipe.show.ShowRecipeFragment
import com.mintthursday.recipelist.RecipeListFragment

object Screens {
    fun recipeList() = FragmentScreen { RecipeListFragment() }
    fun showRecipe(recipe: Recipe) = FragmentScreen { ShowRecipeFragment.newInstance(recipe) }
    fun createNewRecipe() = FragmentScreen { NewRecipeFragment() }
    fun editRecipe(recipe: Recipe) = FragmentScreen { NewRecipeFragment.newInstance(recipe) }
    fun showNewIngredient() = FragmentScreen { IngredientFragment() }
    fun editIngredient(ingredient: Ingredient?, itemPosition: Int) = FragmentScreen { IngredientFragment.newInstance(ingredient, itemPosition) }
}