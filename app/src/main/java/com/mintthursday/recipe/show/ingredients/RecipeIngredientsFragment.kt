package com.mintthursday.recipe.show.ingredients

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mintthursday.App
import com.mintthursday.DecimalDigitsInputFilter
import com.mintthursday.R
import com.mintthursday.databinding.FragmentRecipeIngredientsBinding
import com.mintthursday.models.IngredientChecked
import com.mintthursday.models.Purchase
import com.mintthursday.models.Recipe
import com.mintthursday.recipe.show.ingredients.IngredientShowAdapter.OnItemCheckListener
import java.util.*

class RecipeIngredientsFragment : Fragment() {

    private var _binding: FragmentRecipeIngredientsBinding? = null
    private val binding get() = _binding!!

    private val recipe: Recipe by lazy { arguments?.getParcelable(ARG_RECIPE_INGREDIENTS)!! }

    private var listIngredientChecked = mutableListOf<IngredientChecked>()
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
                val newCount = a.toDouble()
                if (newCount > 0) {
                    listIngredientChecked = listIngredientChecked.map {
                        IngredientChecked(it.ingredient, newCount, it.checked)
                    }.toMutableList()
                    ingredientShowAdapter.setItems(listIngredientChecked)
                    this@RecipeIngredientsFragment.count = newCount
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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

        recipe.ingredients.forEach {
            listIngredientChecked.add(
                IngredientChecked(
                    it,
                    recipe.countPortion.toDouble(),
                    false
                )
            )
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        ingredientShowAdapter =
            IngredientShowAdapter(listIngredientChecked, object : OnItemCheckListener {
                override fun onItemCheck(ingredientChecked: IngredientChecked, itemPosition: Int) {
                    listIngredientChecked.set(itemPosition, ingredientChecked.copy(checked = true))
                    binding.btnAddToCart.setEnabled(true)
                    Log.i("Mint", listIngredientChecked.toString())
                }

                override fun onItemUncheck(
                    ingredientChecked: IngredientChecked,
                    itemPosition: Int
                ) {
                    listIngredientChecked.set(itemPosition, ingredientChecked.copy(checked = false))
                    if (listIngredientChecked.isEmpty()) {
                        binding.btnAddToCart.setEnabled(false)
                    }
                    Log.i("Mint", listIngredientChecked.toString())
                }
            })
        binding.count.setText(recipe.countPortion.toString())
        count = recipe.countPortion.toDouble()
        binding.recyclerView.adapter = ingredientShowAdapter

        binding.btnAddToCart.setOnClickListener {

            val purchases = listIngredientChecked
                .filter { it.checked }
                .map { ingredientChecked ->
                    Purchase(
                        0,
                        recipe.id,
                        ingredientChecked.count,
                        ingredientChecked.ingredient,
                        false
                    )
                }

            App.instance.database.purchaseDao().insertPurchase(purchases)

            binding.root.findNavController()
                .navigate(R.id.action_showRecipeFragment_to_purchasesFragment)
        }
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