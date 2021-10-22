package com.mintthursday.recipe.creation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mintthursday.App
import com.mintthursday.R
import com.mintthursday.databinding.FragmentNewRecipeBinding
import com.mintthursday.models.Ingredient
import com.mintthursday.models.Recipe
import com.mintthursday.models.Step
import com.mintthursday.recipe.creation.SelectCategoryFragment.NoticeDialogListener
import com.mintthursday.recipe.creation.ingredientcreation.IngredientEditAdapter
import com.mintthursday.recipe.creation.ingredientcreation.IngredientEditAdapter.OnIngredientClickListener
import com.mintthursday.recipe.creation.ingredientcreation.IngredientFragment
import com.mintthursday.recipe.creation.stepsadapter.RecyclerRowMoveCallback
import com.mintthursday.recipe.creation.stepsadapter.StepsAdapter

class NewRecipeFragment : Fragment(), NoticeDialogListener {

    private val router = App.instance.router

    private var _binding: FragmentNewRecipeBinding? = null
    private val binding get() = _binding!!

    private val ingredientEditAdapter: IngredientEditAdapter = IngredientEditAdapter()
    private val stepsAdapter: StepsAdapter = StepsAdapter()
    private var idRecipe: Long = 0
    private var chooseCategories: Collection<String> = emptySet()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(INGREDIENT_LIST, ArrayList(ingredientEditAdapter.items))
        outState.putParcelableArrayList(STEP_LIST, ArrayList(stepsAdapter.items))
        outState.putStringArrayList(CATEGORY_LIST, ArrayList(chooseCategories))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val recipe = arguments?.getParcelable<Recipe>(ARG_EDIT_RECIPE)
            if (recipe != null) {
                ingredientEditAdapter.setItems(recipe.ingredients)
                stepsAdapter.setItems(recipe.steps)
                chooseCategories = recipe.category.splitToSet()
            } else {
                stepsAdapter.setItems(startSteps())
            }
        } else {
            savedInstanceState.getParcelableArrayList<Ingredient>(INGREDIENT_LIST)?.let {
                ingredientEditAdapter.setItems(it)
            }
            savedInstanceState.getParcelableArrayList<Step>(STEP_LIST)?.let {
                stepsAdapter.setItems(it)
            }
            savedInstanceState.getStringArrayList(CATEGORY_LIST)?.let {
                chooseCategories = it.toSet()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentNewRecipeBinding.inflate(inflater, container, false)

        binding.textInputName.addTextChangedListener(recipeTextWatcher)
        binding.textInputDescription.addTextChangedListener(recipeTextWatcher)
        binding.textInputQtyPortions.addTextChangedListener(recipeTextWatcher)
        binding.textInputTime.addTextChangedListener(recipeTextWatcher)
        binding.categories.addTextChangedListener(recipeTextWatcher)

        initRecyclerViewIngredients(binding)
        initRecyclerViewSteps(binding)

        if (arguments != null && requireArguments().getParcelable<Recipe>(ARG_EDIT_RECIPE) != null) {
            initEditRecipe(requireArguments().getParcelable(ARG_EDIT_RECIPE)!!)
        }

        setCategories(chooseCategories)

        binding.filledInputCategory.onFocusChangeListener = OnFocusChangeListener { v: View, hasFocus: Boolean ->
            if (v.isInTouchMode && hasFocus) {
                v.performClick() // picks up first tap
            }
        }

        binding.filledInputCategory.setOnClickListener {
            val newFragment: DialogFragment = SelectCategoryFragment.newInstance(chooseCategories)
            newFragment.show(childFragmentManager, DIALOG_CATEGORY)
        }

        binding.filledInputIngredient.onFocusChangeListener = OnFocusChangeListener { v: View, hasFocus: Boolean ->
            if (v.isInTouchMode && hasFocus) {
                v.performClick() // picks up first tap
            }
        }
        binding.filledInputIngredient.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_newRecipeFragment_to_ingredientFragment)
        }


        binding.close.setOnClickListener { router.exit() }//todo navigation
        binding.btnAddStep.setOnClickListener { addStep() }
        binding.btnSaveRecipe.setOnClickListener {
            saveRecipe(buildRecipe())
//            router.exit()
            binding.root.findNavController().navigateUp()
        }

        parentFragmentManager.setFragmentResultListener(IngredientFragment.REQ_INGREDIENT, this, { key, bundle ->
            val result: Ingredient? = bundle.getParcelable(IngredientFragment.BUN_INGREDIENT)
            if (result != null) {
                val ingredient = Ingredient(result.name, result.quantity, result.unit)
                val itemPosition = bundle.getInt(IngredientFragment.BUN_ITEM_POSITION)
                if (itemPosition == -1) {
                    ingredientEditAdapter.addItem(ingredient)
                } else {
                    ingredientEditAdapter.updateItem(ingredient, itemPosition)
                }
                changeVisibilityMinAmountOfIngredient()
            }
        })
        return binding.root
    }

    private fun changeVisibilityMinAmountOfIngredient() {
        if (ingredientEditAdapter.itemCount >= 3) {
            binding.ingredientmin.visibility = View.GONE
        } else {
            binding.ingredientmin.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerViewIngredients(binding: FragmentNewRecipeBinding) {
        ingredientEditAdapter.setOnItemClickListener(object : OnIngredientClickListener {
            override fun onItemClick(ingredient: Ingredient, itemPosition: Int) {
//                router.navigateTo(Screens.editIngredient(ingredient, itemPosition))
                binding.root.findNavController().navigate(R.id.action_newRecipeFragment_to_ingredientFragment, bundleOf(IngredientFragment.ARG_INGREDIENT to ingredient, IngredientFragment.ARG_POSITION to itemPosition))
            }
        })
        binding.ingrRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.ingrRecyclerView.adapter = ingredientEditAdapter
    }

    private fun initEditRecipe(recipe: Recipe) {
        idRecipe = recipe.id
        binding.textInputName.setText(recipe.name)
        binding.textInputDescription.setText(recipe.description)
        binding.textInputQtyPortions.setText(recipe.countPortion.toString())
        binding.textInputTime.setText(recipe.time.toString())
    }

    private fun String.splitToSet(delimiter: String = ", "): Set<String> {
        return this.split(delimiter).toSet()
    }

    private fun initRecyclerViewSteps(binding: FragmentNewRecipeBinding) {
        val stepsRecyclerView: RecyclerView = binding.stepsRecyclerView
        stepsRecyclerView.layoutManager = LinearLayoutManager(this.context)
        val callback: ItemTouchHelper.Callback = RecyclerRowMoveCallback(stepsAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(stepsRecyclerView)
        stepsRecyclerView.adapter = stepsAdapter
    }

    private fun startSteps(): List<Step> {
        return List(3, { Step("") })
    }

    private fun addStep() {
        stepsAdapter.addItem(Step(""))
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, chooseData: Set<String>) {
        setCategories(chooseData)
    }

    private fun setCategories(categories: Collection<String>) {
        this.chooseCategories = categories
        binding.categories.visibility = if (categories.isEmpty()) View.GONE else View.VISIBLE
        binding.categories.text = categories.joinToString(", ")
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {}

    private fun buildRecipe(): Recipe {
        val name = binding.textInputName.text.toString()
        val description = binding.textInputDescription.text.toString()
        var countPortion = 0
        var time = 0
        try {
            countPortion = binding.textInputQtyPortions.text.toString().toInt()
            time = binding.textInputTime.text.toString().toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        val category = binding.categories.text.toString()
        val ingredients: List<Ingredient> = ingredientEditAdapter.items
        val steps: List<Step> = stepsAdapter.items
        val inputLink = binding.filledInputLink.text.toString()
        return Recipe(idRecipe, name, description, countPortion, time, category, ingredients, steps, inputLink)
    }

    private fun saveRecipe(recipe: Recipe) {
        val db = App.instance.database
        db.recipeDao().insertRecipe(recipe)
    }

    private val recipeTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val nameInput = binding.textInputName.text.toString().trim()
            val descriptionInput = binding.textInputDescription.text.toString().trim()
            val quantityInput = binding.textInputQtyPortions.text.toString().trim()
            val timeInput = binding.textInputTime.text.toString().trim()
            val categoryInput = binding.categories.text.toString().trim()

            binding.btnSaveRecipe.isEnabled = nameInput.isNotEmpty() &&
                    descriptionInput.isNotEmpty() &&
                    quantityInput.isNotEmpty() &&
                    timeInput.isNotEmpty() &&
                    categoryInput.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {}

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null //todo зачем мы это сделали?
    }

    companion object {

        const val ARG_EDIT_RECIPE = "ARG_EDIT_RECIPE"
        const val INGREDIENT_LIST = "INGREDIENT_LIST"
        const val STEP_LIST = "STEP_LIST"
        const val CATEGORY_LIST = "CATEGORY_LIST"
        const val DIALOG_CATEGORY = "DIALOG_CATEGORY"


//        fun newInstance(recipe: Recipe): NewRecipeFragment {
//            val fragment = NewRecipeFragment()
//            val bundle = Bundle()
//            bundle.putParcelable(ARG_EDIT_RECIPE, recipe)
//            fragment.arguments = bundle
//            return fragment
//        }
    }
}