package com.mintthursday.recipe.show.description

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mintthursday.databinding.FragmentRecipeDescriptionBinding
import com.mintthursday.models.Recipe

class RecipeDescriptionFragment : Fragment() {

    private var _binding: FragmentRecipeDescriptionBinding? = null
    private val binding get() = _binding!!


//    private val path: String = "/storage/sdcard0/DCIM/Restored/IMG_20190111_140034-COLLAGE.jpg"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentRecipeDescriptionBinding.inflate(inflater, container, false)

        if (requireArguments().getParcelable<Parcelable?>(ARG_RECIPE_DESCRIPTION) != null) {
            val recipe = requireArguments().getParcelable<Parcelable>(ARG_RECIPE_DESCRIPTION) as Recipe?

//            val photo = view.findViewById<View>(R.id.showDescriptionPhoto) as ImageView
//            Glide
//                    .with(view)
//                    .load(path)
//                    .into(photo)
            binding.showDescriptionName.text = recipe!!.name
            binding.showDescriptionCountPortions.text = StringBuilder("Количество порций: ${recipe.countPortion}")
            binding.showDescriptionTime.text = StringBuilder("Время приготовления: ${recipe.time} минут")
            binding.showDescriptionDescription.text = StringBuilder("Описание: ${recipe.description}")
            if (recipe.link.trim() != "") {
                binding.btnShowLink.setEnabled(true)
                binding.btnShowLink.setOnClickListener {
                    val browserIntent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
                    browserIntent.data = Uri.parse(recipe.link)
                    startActivity(browserIntent)
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