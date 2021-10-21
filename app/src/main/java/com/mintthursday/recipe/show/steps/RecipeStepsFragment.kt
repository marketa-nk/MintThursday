package com.mintthursday.recipe.show.steps

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mintthursday.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mintthursday.databinding.FragmentRecipeStepsBinding
import com.mintthursday.models.Recipe

class RecipeStepsFragment : Fragment() {

    private var _binding: FragmentRecipeStepsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentRecipeStepsBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        val stepsShowAdapter = StepsShowAdapter()
        binding.recyclerView.adapter = stepsShowAdapter
        val recipe: Recipe? = requireArguments().getParcelable(ARG_RECIPE_STEPS)
        if (recipe != null) {
            stepsShowAdapter.setItems(recipe.steps)
        }
        return binding.root
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