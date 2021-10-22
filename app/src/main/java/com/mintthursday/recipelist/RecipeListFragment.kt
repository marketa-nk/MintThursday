package com.mintthursday.recipelist

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mintthursday.App.Companion.instance
import com.mintthursday.MainActivity
import com.mintthursday.R
import com.mintthursday.databinding.FragmentRecipeListBinding
import com.mintthursday.models.Recipe
import com.mintthursday.recipe.creation.NewRecipeFragment
import com.mintthursday.recipe.show.ShowRecipeFragment

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private var actionMode: ActionMode? = null
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)

        (activity as? AppCompatActivity)?.setSupportActionBar(binding.myToolbar)
//        binding.fab.setOnClickListener { instance.router.navigateTo(Screens.createNewRecipe()) }
        binding.fab.setOnClickListener { binding.root.findNavController().navigate(R.id.action_recipeListFragment_to_newRecipeFragment) }
        initRecyclerViewMain(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRecipes()
    }

    private fun initRecyclerViewMain(binding: FragmentRecipeListBinding) {
        binding.recipeRecyclerViewMain.layoutManager = LinearLayoutManager(context)
        recipeAdapter = RecipeAdapter()
        binding.recipeRecyclerViewMain.adapter = recipeAdapter
        recipeAdapter.setOnItemClickListenerRecipe(object : OnRecipeClickListener {
            override fun onItemClick(recipe: Recipe, position: Int) {
                if (activity is MainActivity) {
//                    instance.router.navigateTo(showRecipe(recipe))
                    binding.root.findNavController().navigate(R.id.action_recipeListFragment_to_showRecipeFragment, bundleOf(ShowRecipeFragment.ARG_RECIPE to recipe))
                }
            }

            override fun onItemLongClick(recipe: Recipe, position: Int): Boolean {
                if (actionMode != null) {
                    return false
                }
                actionMode = (activity as? AppCompatActivity)?.startSupportActionMode(object : ActionMode.Callback {
                    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                        mode.menuInflater.inflate(R.menu.menu_main_activity, menu)
                        mode.title = "Hi"
                        return true
                    }

                    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                        return false
                    }

                    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                        return when (item.itemId) {
                            R.id.correct_recipe -> {
//                                instance.router.navigateTo(editRecipe(recipe))
                                binding.root.findNavController().navigate(R.id.action_recipeListFragment_to_newRecipeFragment, bundleOf(NewRecipeFragment.ARG_EDIT_RECIPE to recipe))
                                mode.finish()
                                true
                            }
                            R.id.delete_recipe -> {
                                instance.database.recipeDao().delete(recipe)
                                loadRecipes()
                                mode.finish()
                                true
                            }
                            else -> false
                        }
                    }

                    override fun onDestroyActionMode(mode: ActionMode) {
                        actionMode = null
                    }
                })
                return true
            }
        })
    }

    private fun loadRecipes() {
        val recipes = instance.database.recipeDao().getAll()
        recipeAdapter.setItems(recipes)
    }
}