package com.mintthursday.recipe.show.steps

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mintthursday.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mintthursday.models.Recipe

class RecipeStepsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_steps, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val stepsShowAdapter = StepsShowAdapter()
        recyclerView.adapter = stepsShowAdapter
        val recipe: Recipe? = requireArguments().getParcelable(ARG_RECIPE_STEPS)
        if (recipe != null) {
            stepsShowAdapter.setItems(recipe.steps)
        }
        return view
    }

    companion object {
        private const val ARG_RECIPE_STEPS = "ARG_RECIPE_STEPS"
        fun newInstance(recipe: Recipe): RecipeStepsFragment {
            val fragment = RecipeStepsFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_RECIPE_STEPS, recipe)
            fragment.arguments = bundle
            return fragment
        }
    }
}