package com.mintthursday.recipe.show.description

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import com.mintthursday.R
import com.mintthursday.databinding.FragmentRecipeDescriptionBinding
import com.mintthursday.models.Recipe

class RecipeDescriptionFragment : Fragment() {

    private var _binding: FragmentRecipeDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentRecipeDescriptionBinding.inflate(inflater, container, false)

        if (requireArguments().getParcelable<Parcelable?>(ARG_RECIPE_DESCRIPTION) != null) {
            val recipe = requireArguments().getParcelable<Parcelable>(ARG_RECIPE_DESCRIPTION) as Recipe?

            binding.showDescriptionName.text = recipe!!.name
            binding.showDescriptionCountPortions.text = StringBuilder("Количество порций: ${recipe.countPortion}")
            binding.showDescriptionTime.text = StringBuilder("Время приготовления: ${recipe.time} минут")
            binding.showDescriptionDescription.text = StringBuilder("Описание: ${recipe.description}")
            if (recipe.link.trim() != "") {
                binding.btnShowLink.setEnabled(true)
                binding.btnShowLink.setOnClickListener {

                    val colorInt: Int = Color.parseColor("#00bfa5")

                    val defaultColors = CustomTabColorSchemeParams.Builder()
                        .setToolbarColor(colorInt)
                        .build()

                    val customTabsIntent = CustomTabsIntent.Builder()
                        .setDefaultColorSchemeParams(defaultColors)
                        .setStartAnimations(requireContext(), R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim)
                        .build()
                    customTabsIntent.launchUrl(requireContext(), Uri.parse(recipe.link))
                }
            }
        }
        return binding.root
    }

    companion object {
        private const val ARG_RECIPE_DESCRIPTION = "ARG_RECIPE_DESCRIPTION"
        fun newInstance(recipe: Recipe?): RecipeDescriptionFragment {
            val fragment = RecipeDescriptionFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_RECIPE_DESCRIPTION, recipe)
            fragment.arguments = bundle
            return fragment
        }
    }
}