package com.mintthursday.recipe.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.terrakok.cicerone.Screen
import com.mintthursday.R
import com.mintthursday.databinding.FragmentShowRecipeBinding
import com.mintthursday.models.Recipe
import com.mintthursday.recipe.show.description.RecipeDescriptionFragment
import com.mintthursday.recipe.show.ingredients.RecipeIngredientsFragment
import com.mintthursday.recipe.show.steps.RecipeStepsFragment

class ShowRecipeFragment : Fragment(), Screen {

    private var _binding: FragmentShowRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentShowRecipeBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
//        binding.toolbar.setNavigationOnClickListener { App.instance.router.exit() }
        binding.toolbar.setNavigationOnClickListener { binding.root.findNavController().navigateUp() }

        val recipe: Recipe? = requireArguments().getParcelable(ARG_RECIPE)

        if (recipe != null) {
            binding.viewPager.adapter = RecipePagerAdapter(childFragmentManager).apply {
                addFragment(RecipeDescriptionFragment.newInstance(recipe), resources.getString(R.string.description))
                addFragment(RecipeIngredientsFragment.newInstance(recipe), resources.getString(R.string.ingredients))
                addFragment(RecipeStepsFragment.newInstance(recipe), resources.getString(R.string.cook))
            }
            binding.tabLayout.setupWithViewPager(binding.viewPager)
        }
        return binding.root
    }

    companion object {

        const val ARG_RECIPE = "ARG_RECIPE"

//        fun newInstance(recipe: Recipe): ShowRecipeFragment {
//            val fragment = ShowRecipeFragment()
//            val bundle = Bundle()
//            bundle.putParcelable(ARG_RECIPE, recipe)
//            fragment.arguments = bundle
//            return fragment
//        }
    }
}