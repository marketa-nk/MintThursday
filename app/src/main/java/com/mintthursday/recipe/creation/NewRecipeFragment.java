package com.mintthursday.recipe.creation;

import static com.mintthursday.recipe.creation.ingredientcreation.IngredientFragment.BUN_INGREDIENT;
import static com.mintthursday.recipe.creation.ingredientcreation.IngredientFragment.BUN_ITEM_POSITION;
import static com.mintthursday.recipe.creation.ingredientcreation.IngredientFragment.REQ_INGREDIENT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mintthursday.App;
import com.mintthursday.MainActivity;
import com.mintthursday.R;
import com.mintthursday.database.AppDatabase;
import com.mintthursday.database.RecipeDao;
import com.mintthursday.models.Ingredient;
import com.mintthursday.models.Recipe;
import com.mintthursday.models.Step;
import com.mintthursday.recipe.creation.ingredientcreation.IngredientEditAdapter;
import com.mintthursday.recipe.creation.stepsadapter.RecyclerRowMoveCallback;
import com.mintthursday.recipe.creation.stepsadapter.StepsAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewRecipeFragment extends Fragment implements SelectCategoryFragment.NoticeDialogListener {

    public static final String ARG_EDIT_RECIPE = "ARG_EDIT_RECIPE";
    Set<String> chooseData = new HashSet<>();
    private TextView nameRecipe;
    private TextView descriptionRecipe;
    private TextView qtyPortionRecipe;
    private TextView timeRecipe;
    private IngredientEditAdapter ingredientAdapter;
    private StepsAdapter stepsAdapter;
    private TextView editCategories;
    private long idRecipe = 0;

    public NewRecipeFragment() {
    }

    public static NewRecipeFragment newInstance(Recipe recipe) {
        NewRecipeFragment fragment = new NewRecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_EDIT_RECIPE, recipe);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);
        Toolbar myToolbar = view.findViewById(R.id.myToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);
        ImageView closeWindow = view.findViewById(R.id.close);


        editCategories = view.findViewById(R.id.categories);
        View butCattegory = view.findViewById(R.id.outlinedInputCategory);
        View butIngredient = view.findViewById(R.id.outlinedInputIngredient);
        View butAddStep = view.findViewById(R.id.btnAddStep);
        View butSaveRecipe = view.findViewById(R.id.btnSaveRecipe);

        nameRecipe = view.findViewById(R.id.textInputName);
        descriptionRecipe = view.findViewById(R.id.textInputDescription);
        qtyPortionRecipe = view.findViewById(R.id.textInputQtyPortions);
        timeRecipe = view.findViewById(R.id.textInputTime);

        initRecyclerViewIngredients(view);
        initRecyclerViewSteps(view);
        loadSteps();
        if (getArguments() != null && getArguments().getParcelable(ARG_EDIT_RECIPE) != null) {
            Recipe recipe = getArguments().getParcelable(ARG_EDIT_RECIPE);
            initEditRecipe(recipe);
        }


        butCattegory.setOnClickListener(v -> {
            DialogFragment newFragment = SelectCategoryFragment.newInstance(chooseData);
            newFragment.show(getChildFragmentManager(), "abc");
        });
        butCattegory.setOnFocusChangeListener((view1, hasFocus) -> {
            if (view1.isInTouchMode() && hasFocus) {
                view1.performClick();  // picks up first tap
            }
        });
        butIngredient.setOnClickListener(v -> App.getInstance().getRouter().navigateTo(MainActivity.Screens.INSTANCE.showNewIngredient()));
        closeWindow.setOnClickListener(v -> requireActivity().onBackPressed());
        butAddStep.setOnClickListener(v -> addStep());
        butSaveRecipe.setOnClickListener(v -> {
            Recipe recipe = buildRecipe();
            saveRecipe(recipe);
            requireActivity().onBackPressed();
        });
        getParentFragmentManager().setFragmentResultListener(REQ_INGREDIENT, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                Ingredient result = bundle.getParcelable(BUN_INGREDIENT);
                if (result != null) {
                    Ingredient ingredient = new Ingredient(result.getName(), result.getQuantity(), result.getUnit());
                    int itemPosition = bundle.getInt(BUN_ITEM_POSITION);
                    if (itemPosition == -1) {
                        ingredientAdapter.addItem(ingredient);
                    } else {
                        ingredientAdapter.updateItem(ingredient, itemPosition);
                    }
                }
            }
        });
        return view;
    }

    private void initEditRecipe(Recipe recipe) {
        //todo не сохранеются шаги рцепта если открыть редактирование, добавить шшаг. нажать добавить ингредиент и вернуться
        // также с другими полямино тебе везет
        if (recipe != null) {
            idRecipe = recipe.getId();
            nameRecipe.setText(recipe.getName());
            descriptionRecipe.setText(recipe.getDescription());
            qtyPortionRecipe.setText(String.valueOf(recipe.getCountPortion()));
            timeRecipe.setText(String.valueOf(recipe.getTime()));
            editCategories.setText(recipe.getCategory());
            ingredientAdapter.setItems(recipe.getIngredients());
            List<String> list = recipe.getSteps();
            List<Step> steps = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                steps.add(new Step(list.get(i)));
            }
            stepsAdapter.setItems(steps);
        }

    }

    private void initRecyclerViewIngredients(View view) {
        RecyclerView ingrRecyclerView = view.findViewById(R.id.ingrRecyclerView);
        ingrRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ingredientAdapter = new IngredientEditAdapter();
        ingrRecyclerView.setAdapter(ingredientAdapter);
        IngredientEditAdapter.OnIngredientClickListener l = ((ingredient, itemPosition) -> App.getInstance().getRouter().navigateTo(MainActivity.Screens.INSTANCE.editIngredient(ingredient, itemPosition)));
        ingredientAdapter.setOnItemClickListener(l);
    }

    private void initRecyclerViewSteps(View view) {
        RecyclerView stepsRecyclerView = view.findViewById(R.id.stepsRecyclerView);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        stepsAdapter = new StepsAdapter();
        ItemTouchHelper.Callback callback = new RecyclerRowMoveCallback(stepsAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(stepsRecyclerView);
        stepsRecyclerView.setAdapter(stepsAdapter);
    }

    private void loadSteps() {
        List<Step> steps = getSteps();
        stepsAdapter.setItems(steps);
    }

    private List<Step> getSteps() {
        List<Step> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new Step(""));
        }
        return list;
    }

    private void addStep() {
        Step step = getOneStep();
        stepsAdapter.addItem(step);
    }

    private Step getOneStep() {
        return new Step("");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Set<String> chooseData) {
        this.chooseData = chooseData;
        editCategories.setVisibility(View.VISIBLE);
        editCategories.setText(toStringSetWithJoin(chooseData));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    private Recipe buildRecipe() {
        String name = String.valueOf(nameRecipe.getText());
        String description = String.valueOf(descriptionRecipe.getText());
        int countPortion = 0;
        int time = 0;
        try {
            countPortion = Integer.parseInt(qtyPortionRecipe.getText().toString());
            time = Integer.parseInt(timeRecipe.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String category = String.valueOf(editCategories.getText());
        List<Ingredient> ingredients = ingredientAdapter.getItems();
        List<Step> steps = stepsAdapter.getItems();
        List<String> steps2 = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            steps2.add(steps.get(i).getStepInstruction());
        }
        return new Recipe(idRecipe, name, description, countPortion, time, category, ingredients, steps2);

    }

    private void saveRecipe(Recipe recipe) {
        AppDatabase db = App.getInstance().getDatabase();
        RecipeDao recipeDao = db.recipeDao();
        recipeDao.insert(recipe);
    }

    public String toStringSetWithJoin(Set<String> set) {
        StringBuilder result = new StringBuilder();
        String divider = "";
        for (String s : set) {
            result.append(divider).append(s);
            if (divider.isEmpty()) {
                divider = ", ";
            }
        }
        return result.toString();
    }
}