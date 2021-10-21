package com.mintthursday.recipe.creation.ingredientcreation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.mintthursday.App
import com.mintthursday.R
import com.mintthursday.databinding.FragmentIngredientBinding
import com.mintthursday.models.Ingredient

class IngredientFragment : Fragment() {

    private var _binding: FragmentIngredientBinding? = null
    private val binding get() = _binding!!

    private var itemPosition = -1

    private val ingrTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val ingrInput = binding.ingredientName.text.toString().trim { it <= ' ' }
            val qtyInput = binding.ingredientQty.text.toString().trim { it <= ' ' }
            val unitInput = binding.spinnerIngredientUnits.text.toString().trim { it <= ' ' }
            binding.btnSave.isEnabled = ingrInput.isNotEmpty() && qtyInput.isNotEmpty() && unitInput.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentIngredientBinding.inflate(inflater, container, false)

        initToolbar()

        binding.ingredientName.addTextChangedListener(ingrTextWatcher)
        binding.ingredientQty.addTextChangedListener(ingrTextWatcher)
        binding.spinnerIngredientUnits.addTextChangedListener(ingrTextWatcher)

        val type = resources.getStringArray(R.array.ingredient_units_list)
        binding.spinnerIngredientUnits.setAdapter(ArrayAdapter(this.requireContext(), R.layout.dropdown_menu_ingredient_unit, type))

        binding.btnSave.setOnClickListener {
            saveIngredient()
        }

        initEditIngredient()
        return view
    }

    private fun initToolbar() {
        val ingredientToolbar: Toolbar = binding.toolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(ingredientToolbar)
        ingredientToolbar.setNavigationOnClickListener { App.instance.router.exit() }
    }

    private fun initEditIngredient() {
        val arguments = arguments ?: return

        //todo create random Ingredient.id
        itemPosition = arguments.getInt(ARG_POSITION)

        val ingredient = arguments.getParcelable<Ingredient>(ARG_INGREDIENT) ?: return
        binding.ingredientName.setText(ingredient.name)
        binding.ingredientQty.setText(ingredient.quantity.toString())
        binding.spinnerIngredientUnits.setText(ingredient.unit)
    }

    private fun saveIngredient() {
        val ingredient = buildIngredient()

        val bundle = bundleOf(BUN_INGREDIENT to ingredient, BUN_ITEM_POSITION to itemPosition)

        parentFragmentManager.setFragmentResult(REQ_INGREDIENT, bundle)
        App.instance.router.exit()
    }

    private fun buildIngredient(): Ingredient {
        val name = binding.ingredientName.text.toString()
        val stringQuantity = binding.ingredientQty.text.toString()
        val quantity: Double = if (stringQuantity.isNotEmpty()) {
            stringQuantity.toDouble()
        } else {
            0.0
        }

        val unit = binding.spinnerIngredientUnits.text.toString()

        return Ingredient(name, quantity, unit)
    }

    companion object {

        const val ARG_INGREDIENT = "ARG_INGREDIENT"
        const val ARG_POSITION = "ARG_POSITION"
        const val BUN_INGREDIENT = "BUN_INGREDIENT"
        const val BUN_ITEM_POSITION = "BUN_ITEM_POSITION"
        const val REQ_INGREDIENT = "REQ_INGREDIENT"

        fun newInstance(ingredient: Ingredient?, itemPosition: Int): IngredientFragment {
            val fragment = IngredientFragment()
            fragment.arguments = bundleOf(ARG_INGREDIENT to ingredient, ARG_POSITION to itemPosition)
            return fragment
        }
    }
}