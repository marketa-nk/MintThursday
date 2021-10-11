package com.mintthursday.recipe.creation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mintthursday.App
import com.mintthursday.R
import com.mintthursday.Screens
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

    private lateinit var nameRecipe: TextView
    private lateinit var descriptionRecipe: TextView
    private lateinit var qtyPortionRecipe: TextView
    private lateinit var timeRecipe: TextView
    private lateinit var link: TextView
    private lateinit var ingredientEditAdapter: IngredientEditAdapter
    private lateinit var stepsAdapter: StepsAdapter
    private lateinit var editCategories: TextView
    private lateinit var butSaveRecipe: Button
    private var idRecipe: Long = 0
    private var chooseData: Collection<String> = emptySet()
    private var ingredientList: List<Ingredient> = emptyList()
    private var stepList: List<Step>? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(INGREDIENT_LIST, ArrayList(ingredientEditAdapter.items))
        outState.putParcelableArrayList(STEP_LIST, ArrayList(stepsAdapter.items))
        outState.putStringArrayList(CATEGORY_LIST, ArrayList(chooseData))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            savedInstanceState.getParcelableArrayList<Ingredient>(INGREDIENT_LIST)?.let {
                ingredientList = it
                ingredientEditAdapter.setItems(it)
            }
            savedInstanceState.getParcelableArrayList<Step>(STEP_LIST)?.let {
                stepList = it
                stepsAdapter.setItems(it)
            }
            savedInstanceState.getStringArrayList(CATEGORY_LIST)?.let { setCategories(it.toSet()) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_new_recipe, container, false)
        val closeWindow = view.findViewById<ImageView>(R.id.close)
        val butCategory = view.findViewById<View>(R.id.filledInputCategory)
        val butIngredient = view.findViewById<View>(R.id.filledInputIngredient)
        val butAddStep = view.findViewById<View>(R.id.btnAddStep)
        val chipIngr = view.findViewById<View>(R.id.ingredientmin)

        butSaveRecipe = view.findViewById(R.id.btnSaveRecipe)
        nameRecipe = view.findViewById(R.id.textInputName)
        nameRecipe.addTextChangedListener(recipeTextWatcher)
        descriptionRecipe = view.findViewById(R.id.textInputDescription)
        descriptionRecipe.addTextChangedListener(recipeTextWatcher)
        qtyPortionRecipe = view.findViewById(R.id.textInputQtyPortions)
        qtyPortionRecipe.addTextChangedListener(recipeTextWatcher)
        timeRecipe = view.findViewById(R.id.textInputTime)
        timeRecipe.addTextChangedListener(recipeTextWatcher)
        editCategories = view.findViewById(R.id.categories)
        editCategories.addTextChangedListener(recipeTextWatcher)
        link = view.findViewById(R.id.filledInputLink)

        initRecyclerViewIngredients(view)
        initRecyclerViewSteps(view)

        if (arguments != null && requireArguments().getParcelable<Recipe>(ARG_EDIT_RECIPE) != null) {
            initEditRecipe(requireArguments().getParcelable(ARG_EDIT_RECIPE)!!)
        }

        butCategory.setOnClickListener {
            val newFragment: DialogFragment = SelectCategoryFragment.newInstance(chooseData)
            newFragment.show(childFragmentManager, DIALOG_CATEGORY)
        }

        butCategory.onFocusChangeListener = OnFocusChangeListener { v: View, hasFocus: Boolean ->
            if (v.isInTouchMode && hasFocus) {
                v.performClick() // picks up first tap
            }
        }

        butIngredient.setOnClickListener { router.navigateTo(Screens.showNewIngredient()) }
        butIngredient.onFocusChangeListener = OnFocusChangeListener { v: View, hasFocus: Boolean ->
            if (v.isInTouchMode && hasFocus) {
                v.performClick() // picks up first tap
            }
        }

        closeWindow.setOnClickListener { router.exit() }
        butAddStep.setOnClickListener { addStep() }
        butSaveRecipe.setOnClickListener {
            saveRecipe(buildRecipe())
            router.exit()
        }

        parentFragmentManager.setFragmentResultListener(IngredientFragment.REQ_INGREDIENT, this, { key, bundle ->
            val result: Ingredient? = bundle.getParcelable(IngredientFragment.BUN_INGREDIENT)
            if (result != null) {
                val ingredient = Ingredient(result.name, result.quantity, result.unit)
                val itemPosition = bundle.getInt(IngredientFragment.BUN_ITEM_POSITION)
                if (itemPosition == -1) {
                    ingredientEditAdapter.addItem(ingredient)
                    ingredientList = ingredientEditAdapter.items
                } else {
                    ingredientEditAdapter.updateItem(ingredient, itemPosition)
                }
            }

            if (ingredientEditAdapter.itemCount >= 3) {
                chipIngr.visibility = View.GONE
            }
        })
        return view
    }

    private fun initRecyclerViewIngredients(view: View) {
        ingredientEditAdapter = IngredientEditAdapter()
        ingredientEditAdapter.setOnItemClickListener(object : OnIngredientClickListener {
            override fun onItemClick(ingredient: Ingredient, itemPosition: Int) {
                router.navigateTo(Screens.editIngredient(ingredient, itemPosition))
            }
        })
        view.findViewById<RecyclerView>(R.id.ingrRecyclerView).apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = ingredientEditAdapter
        }
        ingredientEditAdapter.setItems(ingredientList)
    }

    private fun initEditRecipe(recipe: Recipe) {
        idRecipe = recipe.id
        nameRecipe.text = recipe.name
        descriptionRecipe.text = recipe.description
        qtyPortionRecipe.text = recipe.countPortion.toString()
        timeRecipe.text = recipe.time.toString()
        setCategories(recipe.category.splitToSet())
        ingredientEditAdapter.setItems(recipe.ingredients)
        stepsAdapter.setItems(recipe.steps)
    }

    private fun String.splitToSet(delimiter: String = ", "): Set<String> {
        return this.split(delimiter).toSet()
    }

    private fun initRecyclerViewSteps(view: View) {
        val stepsRecyclerView: RecyclerView = view.findViewById(R.id.stepsRecyclerView)
        stepsRecyclerView.layoutManager = LinearLayoutManager(this.context)
        stepsAdapter = StepsAdapter()
        val callback: ItemTouchHelper.Callback = RecyclerRowMoveCallback(stepsAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(stepsRecyclerView)
        stepsRecyclerView.adapter = stepsAdapter
        if (stepList != null) {
            stepsAdapter.setItems(stepList.orEmpty())
        } else {
            loadSteps()
        }
    }

    private fun loadSteps() {
        val steps = List(3, { Step("") })
        stepsAdapter.setItems(steps)
    }

    private fun addStep() {
        stepsAdapter.addItem(Step(""))
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, chooseData: Set<String>) {
        setCategories(chooseData)
    }

    private fun setCategories(categories: Collection<String>) {
        this.chooseData = categories
        editCategories.visibility = if (categories.isEmpty()) View.GONE else View.VISIBLE
        editCategories.text = categories.joinToString(", ")
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {}

    private fun buildRecipe(): Recipe {
        val name = nameRecipe.text.toString()
        val description = descriptionRecipe.text.toString()
        var countPortion = 0
        var time = 0
        try {
            countPortion = qtyPortionRecipe.text.toString().toInt()
            time = timeRecipe.text.toString().toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        val category = editCategories.text.toString()
        val ingredients: List<Ingredient> = ingredientEditAdapter.items
        val steps: List<Step> = stepsAdapter.items
        val inputLink = link.text.toString()
        return Recipe(idRecipe, name, description, countPortion, time, category, ingredients, steps, inputLink)
    }

    private fun saveRecipe(recipe: Recipe) {
        val db = App.instance.database
        db.recipeDao().insert(recipe)
    }

    private val recipeTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val nameInput = nameRecipe.text.toString().trim()
            val descriptionInput = descriptionRecipe.text.toString().trim()
            val quantityInput = qtyPortionRecipe.text.toString().trim()
            val timeInput = timeRecipe.text.toString().trim()
            val categoryInput = editCategories.text.toString().trim()

            butSaveRecipe.isEnabled = nameInput.isNotEmpty() && descriptionInput.isNotEmpty() && quantityInput.isNotEmpty() && timeInput.isNotEmpty() && categoryInput.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {}

    }

    companion object {

        const val ARG_EDIT_RECIPE = "ARG_EDIT_RECIPE"
        const val INGREDIENT_LIST = "INGREDIENT_LIST"
        const val STEP_LIST = "STEP_LIST"
        const val CATEGORY_LIST = "CATEGORY_LIST"
        const val DIALOG_CATEGORY = "DIALOG_CATEGORY"


        fun newInstance(recipe: Recipe): NewRecipeFragment {
            val fragment = NewRecipeFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_EDIT_RECIPE, recipe)
            fragment.arguments = bundle
            return fragment
        }
    }
}