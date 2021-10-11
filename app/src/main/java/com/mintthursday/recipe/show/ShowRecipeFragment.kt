package com.mintthursday.recipe.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.github.terrakok.cicerone.Screen
import com.google.android.material.tabs.TabLayout
import com.mintthursday.App
import com.mintthursday.R
import com.mintthursday.models.Recipe
import com.mintthursday.recipe.show.description.RecipeDescriptionFragment
import com.mintthursday.recipe.show.ingredients.RecipeIngredientsFragment
import com.mintthursday.recipe.show.steps.RecipeStepsFragment

class ShowRecipeFragment : Fragment(), Screen {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_show_recipe, container, false)

        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)
        val ingredientToolbar: Toolbar = view.findViewById(R.id.toolbar)

        (activity as AppCompatActivity?)!!.setSupportActionBar(ingredientToolbar)
        ingredientToolbar.setNavigationOnClickListener { App.instance.router.exit() }

        val recipe: Recipe? = requireArguments().getParcelable(ARG_RECIPE)

        if (recipe != null) {
            viewPager.adapter = RecipePagerAdapter(childFragmentManager).apply {
                addFragment(RecipeDescriptionFragment.newInstance(recipe), resources.getString(R.string.description))
                addFragment(RecipeIngredientsFragment.newInstance(recipe), resources.getString(R.string.ingredients))
                addFragment(RecipeStepsFragment.newInstance(recipe), resources.getString(R.string.cook))
            }
            tabLayout.setupWithViewPager(viewPager)
        }
        return view
    }

    companion object {

        private const val ARG_RECIPE = "ARG_RECIPE"

        fun newInstance(recipe: Recipe): ShowRecipeFragment {
            val fragment = ShowRecipeFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_RECIPE, recipe)
            fragment.arguments = bundle
            return fragment
        }
    }
}