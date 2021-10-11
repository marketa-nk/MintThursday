package com.mintthursday.recipe.show.ingredients

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mintthursday.DecimalDigitsInputFilter
import com.mintthursday.R
import com.mintthursday.models.Ingredient
import com.mintthursday.models.Recipe
import com.mintthursday.recipe.show.ingredients.IngredientShowAdapter.OnItemCheckListener
import java.util.*

class RecipeIngredientsFragment : Fragment() {

    private val recipe: Recipe by lazy { arguments?.getParcelable(ARG_RECIPE_INGREDIENTS)!! }

    private val currentSelectedIngredients = mutableListOf<Ingredient>()
    private var count = 0.0
    private lateinit var ingredientShowAdapter: IngredientShowAdapter
    private lateinit var countPortions: EditText

    private val countTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            var a = s.toString().trim { it <= ' ' }
            if (a.isNotEmpty()) {
                if (a.startsWith(".")) {
                    a = "0$a"
                    countPortions.setText(a)
                    countPortions.setSelection(countPortions.text.length)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false)
        val btnMinus = view.findViewById<ImageButton>(R.id.minus)
        val btnPlus = view.findViewById<ImageButton>(R.id.plus)
        val btnAddToCart: Button = view.findViewById(R.id.addToCart)
        countPortions = view.findViewById(R.id.count)

        btnMinus.setOnClickListener({
            if (count > 1) {
                count--
                countPortions.setText(count.toString())
            }
        })
        btnPlus.setOnClickListener({
            count++
            countPortions.setText(count.toString())
        })

        countPortions.addTextChangedListener(countTextWatcher)
        countPortions.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(2)))


        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        ingredientShowAdapter = IngredientShowAdapter(recipe.ingredients, object : OnItemCheckListener {
            override fun onItemCheck(ingredient: Ingredient) {
                currentSelectedIngredients.add(ingredient)
                btnAddToCart.setEnabled(true)
                Log.i("Mint", currentSelectedIngredients.toString())
            }

            override fun onItemUncheck(ingredient: Ingredient) {
                currentSelectedIngredients.remove(ingredient)
                if (currentSelectedIngredients.isEmpty()) {
                    btnAddToCart.setEnabled(false)
                }
                Log.i("Mint", currentSelectedIngredients.toString())
            }
        })
        countPortions.setText(recipe.countPortion.toString())
        count = recipe.countPortion.toDouble()
        recyclerView.adapter = ingredientShowAdapter
        return view
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