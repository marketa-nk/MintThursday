package com.mintthursday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            App.instance.router.navigateTo(Screens.recipeList())
        }
    }

    private val navigator: Navigator = AppNavigator(this, R.id.container)
    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.instance.navigatorHolder.removeNavigator()
        super.onPause()
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

}