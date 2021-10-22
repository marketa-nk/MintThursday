package com.mintthursday.recipe.show.ingredients

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mintthursday.DecimalDigitsInputFilter
import com.mintthursday.databinding.FragmentRecipeIngredientsBinding
import com.mintthursday.models.Ingredient
import com.mintthursday.models.Recipe
import com.mintthursday.recipe.show.ingredients.IngredientShowAdapter.OnItemCheckListener
import java.util.*

class RecipeIngredientsFragment : Fragment() {

    private var _binding: FragmentRecipeIngredientsBinding? = null
    private val binding get() = _binding!!

    private val recipe: Recipe by lazy { arguments?.getParcelable(ARG_RECIPE_INGREDIENTS)!! }

    private val currentSelectedIngredients = mutableListOf<Ingredient>()
    private var count = 0.0
    private lateinit var ingredientShowAdapter: IngredientShowAdapter

    private val countTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            var a = s.toString().trim { it <= ' ' }
            if (a.isNotEmpty()) {
                if (a.startsWith(".")) {
                    a = "0$a"
                    binding.count.setText(a)
                    binding.count.setSelection(binding.count.text.length)
                }
                val count = a.toDouble()
                if (count > 0) {
                    val ingredientList = recipe.ingredients
                    val listNew = mutableListOf<Ingredient>()
                    for (i in ingredientList.indices) {
                        val qty = recipe.ingredients[i].quantity / recipe.countPortion * count
                        val newIngredient = Ingredient(ingredientList[i].name, qty, ingredientList[i].unit)
                        listNew.add(newIngredient)
                        if (currentSelectedIngredients.isNotEmpty()) {
                            for (j in currentSelectedIngredients.indices) {
                                if (newIngredient.name == currentSelectedIngredients[j].name) {
                                    currentSelectedIngredients[j] = newIngredient
                                    Log.i("Mint", currentSelectedIngredients.toString())
                                    break
                                }
                            }
                        }
                    }
                    ingredientShowAdapter.setItems(listNew)
                    this@RecipeIngredientsFragment.count = count
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentRecipeIngredientsBinding.inflate(inflater, container, false)

        binding.btnMinus.setOnClickListener({
            if (count > 1) {
                count--
                binding.count.setText(count.toString())
            }
        })
        binding.btnPlus.setOnClickListener({
            count++
            binding.count.setText(count.toString())
        })

        binding.count.addTextChangedListener(countTextWatcher)
        binding.count.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(2)))

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        ingredientShowAdapter = IngredientShowAdapter(recipe.ingredients, object : OnItemCheckListener {
            override fun onItemCheck(ingredient: Ingredient) {
                currentSelectedIngredients.add(ingredient)
                binding.btnAddToCart.setEnabled(true)
                Log.i("Mint", currentSelectedIngredients.toString())
            }

            override fun onItemUncheck(ingredient: Ingredient) {
                currentSelectedIngredients.remove(ingredient)
                if (currentSelectedIngredients.isEmpty()) {
                    binding.btnAddToCart.setEnabled(false)
                }
                Log.i("Mint", currentSelectedIngredients.toString())
            }
        })
        binding.count.setText(recipe.countPortion.toString())
        count = recipe.countPortion.toDouble()
        binding.recyclerView.adapter = ingredientShowAdapter
        return binding.root
    }

    companion object {
        private const val ARG_RECIPE_INGREDIENTS = "ARG_RECIPE_INGREDIENTS"
        fun newInstance(recipe: Recipe): RecipeIngredientsFragment {
            val fragment = RecipeIngredientsFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_RECIPE_INGREDIENTS, recipe)
            fragment.arguments = bundle
            return fragment
        }
    }
}