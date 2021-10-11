package com.mintthursday.recipe.show.description

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mintthursday.R
import com.mintthursday.models.Recipe

class RecipeDescriptionFragment : Fragment() {

    private val path: String = "/storage/sdcard0/DCIM/Restored/IMG_20190111_140034-COLLAGE.jpg"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_description, container, false)
        if (requireArguments().getParcelable<Parcelable?>(ARG_RECIPE_DESCRIPTION) != null) {
            val recipe = requireArguments().getParcelable<Parcelable>(ARG_RECIPE_DESCRIPTION) as Recipe?
            val descriptionName = view.findViewById<View>(R.id.showDescriptionName) as TextView
            val photo = view.findViewById<View>(R.id.showDescriptionPhoto) as ImageView
            val descriptionCountPortions = view.findViewById<View>(R.id.showDescriptionCountPortions) as TextView
            val descriptionTime = view.findViewById<View>(R.id.showDescriptionTime) as TextView
            val descriptionDescription = view.findViewById<View>(R.id.showDescriptionDescription) as TextView
            val link = view.findViewById<View>(R.id.btn_showLink) as Button
            Glide
                    .with(view)
                    .load(path)
                    .into(photo)
            descriptionName.text = recipe!!.name
            descriptionCountPortions.text = StringBuilder("Количество порций: ${recipe.countPortion}")
            descriptionTime.text = StringBuilder("Время приготовления: ${recipe.time} минут")
            descriptionDescription.text = StringBuilder("Описание: ${recipe.description}")
            if (recipe.link.trim() != "") {
                link.setEnabled(true)
                link.setOnClickListener {
                    val browserIntent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
                    browserIntent.data = Uri.parse(recipe.link)
                    startActivity(browserIntent)
                }
            }
        }
        return view
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