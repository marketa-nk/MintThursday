package com.mintthursday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mintthursday.models.Ingredient
import com.mintthursday.models.Recipe
import com.mintthursday.recipe.creation.NewRecipeFragment
import com.mintthursday.recipe.creation.ingredientcreation.IngredientFragment
import com.mintthursday.recipe.show.ShowRecipeFragment
import com.mintthursday.recipelist.RecipeListFragment

class MainActivity : AppCompatActivity(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, RecipeListFragment())
                    .commit()
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun showRecipe(recipe: Recipe?) {
        val showRecipeFragment: Fragment = ShowRecipeFragment.newInstance(recipe)
        openFragment(showRecipeFragment)
    }

    override fun createNewRecipe() {
        openFragment(NewRecipeFragment())
    }

    override fun editRecipe(recipe: Recipe?) {
        openFragment(NewRecipeFragment.newInstance(recipe))
    }

    override fun showNewIngredient() {
        openFragment(IngredientFragment())
    }

    override fun editIngredient(ingredient: Ingredient?, itemPosition: Int) {
        openFragment(IngredientFragment.newInstance(ingredient, itemPosition))
    }
}