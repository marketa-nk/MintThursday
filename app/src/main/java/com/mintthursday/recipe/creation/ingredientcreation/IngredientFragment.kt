package com.mintthursday.recipe.creation.ingredientcreation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.mintthursday.App
import com.mintthursday.R
import com.mintthursday.models.Ingredient

class IngredientFragment : Fragment() {

    private lateinit var btnSave: Button
    private lateinit var ingrQty: EditText
    private lateinit var ingrName: EditText
    private lateinit var ingrUnit: MaterialAutoCompleteTextView
    private var itemPosition = -1

    private val ingrTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val ingrInput = ingrName.text.toString().trim { it <= ' ' }
            val qtyInput = ingrQty.text.toString().trim { it <= ' ' }
            val unitInput = ingrUnit.text.toString().trim { it <= ' ' }
            btnSave.isEnabled = ingrInput.isNotEmpty() && qtyInput.isNotEmpty() && unitInput.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ingredient, container, false)

        initToolbar(view)

        btnSave = view.findViewById(R.id.btnSave)
        ingrQty = view.findViewById(R.id.textInputEditTextQty)
        ingrName = view.findViewById(R.id.textInputEditTextName)
        ingrUnit = view.findViewById(R.id.spinnerIngredUnits)

        ingrName.addTextChangedListener(ingrTextWatcher)
        ingrQty.addTextChangedListener(ingrTextWatcher)
        ingrUnit.addTextChangedListener(ingrTextWatcher)

        val type = resources.getStringArray(R.array.ingredient_units_list)
        ingrUnit.setAdapter(ArrayAdapter(this.requireContext(), R.layout.dropdown_menu_ingredient_unit, type))

        btnSave.setOnClickListener {
            saveIngredient()
        }

        initEditIngredient()
        return view
    }

    private fun initToolbar(view: View) {
        val ingredientToolbar: Toolbar = view.findViewById(R.id.toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(ingredientToolbar)
        ingredientToolbar.setNavigationOnClickListener { App.instance.router.exit() }
    }

    private fun initEditIngredient() {
        val arguments = arguments ?: return

        //todo create random Ingredient.id
        itemPosition = arguments.getInt(ARG_POSITION)

        val ingredient = arguments.getParcelable<Ingredient>(ARG_INGREDIENT) ?: return
        ingrName.setText(ingredient.name)
        ingrQty.setText(ingredient.quantity.toString())
        ingrUnit.setText(ingredient.unit)
    }

    private fun saveIngredient() {
        val ingredient = buildIngredient()

        val bundle = bundleOf(BUN_INGREDIENT to ingredient, BUN_ITEM_POSITION to itemPosition)

        parentFragmentManager.setFragmentResult(REQ_INGREDIENT, bundle)
        App.instance.router.exit()
    }

    private fun buildIngredient(): Ingredient {
        val name = ingrName.text.toString()
        val stringQuantity = ingrQty.text.toString()
        val quantity: Double = if (stringQuantity.isNotEmpty()) {
            stringQuantity.toDouble()
        } else {
            0.0
        }

        val unit = ingrUnit.text.toString()

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
            fragment.arguments = bundleOf(ARG_INGREDIENT to ingredient, ARG_POSITION to itemPosition) //todo bundleOf - done
            return fragment
        }
    }
}