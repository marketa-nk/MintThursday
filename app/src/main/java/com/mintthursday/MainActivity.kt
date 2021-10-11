package com.mintthursday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mintthursday.models.Ingredient
import com.mintthursday.models.Recipe
import com.mintthursday.recipe.creation.NewRecipeFragment
import com.mintthursday.recipe.creation.ingredientcreation.IngredientFragment
import com.mintthursday.recipe.show.ShowRecipeFragment
import com.mintthursday.recipelist.RecipeListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            App.getInstance().router.navigateTo(Screens.recipeList())
        }
    }

    private val navigator: Navigator = AppNavigator(this, R.id.container)
    override fun onResumeFragments() {
        super.onResumeFragments()
        App.getInstance().navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.getInstance().navigatorHolder.removeNavigator()
        super.onPause()
    }

    object Screens {
        fun recipeList() = FragmentScreen { RecipeListFragment() }
        fun showRecipe(recipe: Recipe?) = FragmentScreen { ShowRecipeFragment.newInstance(recipe) }
        fun createNewRecipe() = FragmentScreen { NewRecipeFragment() }
        fun editRecipe(recipe: Recipe?) = FragmentScreen { NewRecipeFragment.newInstance(recipe) }
        fun showNewIngredient() = FragmentScreen { IngredientFragment() }
        fun editIngredient(ingredient: Ingredient?, itemPosition: Int) = FragmentScreen { IngredientFragment.newInstance(ingredient, itemPosition) }
    }

//    private fun openFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.container, fragment)
//                .addToBackStack(null)
//                .commit()
//    }

//    override fun showRecipe(recipe: Recipe?) {
//        val showRecipeFragment: Fragment = ShowRecipeFragment.newInstance(recipe)
//    }

//    override fun createNewRecipe() {
//        openFragment(NewRecipeFragment())
//    }

//    override fun editRecipe(recipe: Recipe?) {
//        openFragment(NewRecipeFragment.newInstance(recipe))
//    }
//
//    override fun showNewIngredient() {
//        openFragment(IngredientFragment())
//    }
//
//    override fun editIngredient(ingredient: Ingredient?, itemPosition: Int) {
//        openFragment(IngredientFragment.newInstance(ingredient, itemPosition))
//    }
}