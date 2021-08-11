package com.mintthursday;

import static com.mintthursday.IngredientActivity.ARG_INGREDIENT;
import static com.mintthursday.IngredientActivity.ARG_POSITION;
import static com.mintthursday.IngredientActivity.INTENT_RESULT_ARG_NAME;
import static com.mintthursday.IngredientActivity.INTENT_RESULT_ARG_POSITION;
import static com.mintthursday.IngredientActivity.INTENT_RESULT_ARG_QUANTITY;
import static com.mintthursday.IngredientActivity.INTENT_RESULT_ARG_UNIT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NewRecipeActivity extends AppCompatActivity implements CategoryFragment.NoticeDialogListener {

    private static int REQUEST_CODE_NEW_INGREDIENT = 1;
    private static int REQUEST_CODE_EDIT_INGREDIENT = 2;
    TextView descriptionRecipe;
    TextView nameRecipe;
    TextView qtyPortionRecipe;
    TextView timeRecipe;
    RecyclerView ingrRecyclerView;
    RecyclerView stepsRecyclerView;
    private IngredientAdapter ingredientAdapter;
    private StepsAdapter stepsAdapter;
    private TextView editCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        ImageView closeWindow = findViewById(R.id.close);


        editCategories = findViewById(R.id.categories);
        View butCattegory = findViewById(R.id.outlinedInputCategory);
        View butIngredient = findViewById(R.id.outlinedInputIngredient);
        View butAddStep = findViewById(R.id.btnAddStep);
        View butSaveRecipe = findViewById(R.id.btnSaveRecipe);

        descriptionRecipe = findViewById(R.id.textInputDescription);
        nameRecipe = findViewById(R.id.textInputName);
        qtyPortionRecipe = findViewById(R.id.textInputQtyPortions);
        timeRecipe = findViewById(R.id.textInputTime);

        ingrRecyclerView = findViewById(R.id.ingrRecyclerView);
        stepsRecyclerView = findViewById(R.id.stepsRecyclerView);


        initRecyclerViewIngredients();
        initRecyclerViewSteps();
        loadSteps();

        butCattegory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new CategoryFragment();
                newFragment.show(getSupportFragmentManager(), "abc");


            }
        });
        butCattegory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (view.isInTouchMode() && hasFocus) {
                    view.performClick();  // picks up first tap
                }
            }
        });
        butIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(NewRecipeActivity.this, IngredientActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_INGREDIENT); //fixme

            }
        });
        closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        butAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStep();
            }
        });
        butSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipe = buildRecipe();
                saveRecipe(recipe);
                onBackPressed();
            }
        });


    }

    private void initRecyclerViewIngredients() {
        RecyclerView ingrRecyclerView = findViewById(R.id.ingrRecyclerView);
        ingrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapter = new IngredientAdapter();
        ingrRecyclerView.setAdapter(ingredientAdapter);
        OnItemClickListener l = (new OnItemClickListener() {
            @Override
            public void onItemClick(Ingredient ingredient, int itemPosition) {
                Intent intent = new Intent(NewRecipeActivity.this, IngredientActivity.class);
                intent.putExtra(ARG_INGREDIENT, ingredient);
                intent.putExtra(ARG_POSITION, itemPosition);
                startActivityForResult(intent, REQUEST_CODE_EDIT_INGREDIENT);
            }
        });
        ingredientAdapter.setOnItemClickListener(l);
    }

    private void initRecyclerViewSteps() {
        RecyclerView stepsRecyclerView = findViewById(R.id.stepsRecyclerView);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            list.add(new Step(i + " Напишите инструкцию…"));
        }
        return list;
    }

    private void addStep() {
        Step step = getOneStep();
        stepsAdapter.addItem(step);
    }

    private Step getOneStep() {
        Step oneStep = new Step("Напишите инструкцию… ");
        return oneStep;
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new DialogFragment();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Set<String> chooseData) {
        editCategories.setVisibility(View.VISIBLE);
        editCategories.setText(toStringSetWithJoin(chooseData));

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_CODE_EDIT_INGREDIENT || requestCode == REQUEST_CODE_NEW_INGREDIENT) && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String name = data.getStringExtra(INTENT_RESULT_ARG_NAME);
                double quantity = data.getDoubleExtra(INTENT_RESULT_ARG_QUANTITY, 0);
                String unit = data.getStringExtra(INTENT_RESULT_ARG_UNIT);
                Integer itemPosition = (Integer) data.getSerializableExtra(INTENT_RESULT_ARG_POSITION);
                Ingredient ingredient = new Ingredient(name, quantity, unit);
                if (itemPosition != null) {
                    ingredientAdapter.updateItem(ingredient, itemPosition);
                } else {
                    ingredientAdapter.addItem(ingredient);
                }


            }
        }
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
        return new Recipe(name, description, countPortion, time, category, ingredients, steps2);

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