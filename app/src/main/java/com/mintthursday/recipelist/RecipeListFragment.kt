package com.mintthursday.recipelist

import com.mintthursday.App.Companion.instance
import com.mintthursday.Screens.createNewRecipe
import com.mintthursday.Screens.showRecipe
import com.mintthursday.Screens.editRecipe
import android.os.Bundle
import android.view.*
import com.mintthursday.R
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mintthursday.MainActivity
import com.mintthursday.databinding.FragmentRecipeListBinding
import com.mintthursday.models.Recipe

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private var actionMode: ActionMode? = null
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)


        (activity as? AppCompatActivity)?.setSupportActionBar(binding.myToolbar)
        binding.fab.setOnClickListener { instance.router.navigateTo(createNewRecipe()) }
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
                    instance.router.navigateTo(showRecipe(recipe))
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
                                instance.router.navigateTo(editRecipe(recipe))
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