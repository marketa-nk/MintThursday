package ru.startandroid.mintthursday;

import static ru.startandroid.mintthursday.IngredientActivity.INTENT_RESULT_ARG_NAME;
import static ru.startandroid.mintthursday.IngredientActivity.INTENT_RESULT_ARG_QUANTITY;
import static ru.startandroid.mintthursday.IngredientActivity.INTENT_RESULT_ARG_UNIT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NewRecipeActivity extends AppCompatActivity implements CategoryFragment.NoticeDialogListener {

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

        TextView nameRecipe = findViewById(R.id.textInputName);
        TextView descriptionRecipe = findViewById(R.id.textInputDescription);
        TextView qtyPortionRecipe = findViewById(R.id.textInputQtyPortions);
        TextView timeRecipe = findViewById(R.id.textInputTime);

        RecyclerView ingrRecyclerView = findViewById(R.id.ingrRecyclerView);
        RecyclerView stepsRecyclerView = findViewById(R.id.stepsRecyclerView);






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
                String name = String.valueOf(nameRecipe.getText());
                String description = String.valueOf(descriptionRecipe.getText());
                int countPortion = Integer.parseInt(qtyPortionRecipe.getText().toString());
                int time = Integer.parseInt(timeRecipe.getText().toString());
                String category = String.valueOf(editCategories.getText());
                List<Ingredient> ingredients = ingredientAdapter.getItems();
                List<Step> steps= stepsAdapter.getItems();
                List<String> steps2 = new ArrayList<>();
                for (int i = 0; i < steps.size(); i++){
                    steps2.add(steps.get(i).getStepInstruction());
                }
                Recipe recipe = new Recipe(name,description,countPortion,time,category,ingredients,steps2);
                System.out.println("recipe = "+recipe);
            }
        });


    }

    private void initRecyclerViewIngredients() {
        RecyclerView ingrRecyclerView = findViewById(R.id.ingrRecyclerView);
        ingrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapter = new IngredientAdapter();
        ingrRecyclerView.setAdapter(ingredientAdapter);
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
        if (requestCode == REQUEST_CODE_NEW_INGREDIENT && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String name = data.getStringExtra(INTENT_RESULT_ARG_NAME);
                double quantity = data.getDoubleExtra(INTENT_RESULT_ARG_QUANTITY, 0);
                String unit = data.getStringExtra(INTENT_RESULT_ARG_UNIT);

                Ingredient ingredient = new Ingredient(name, quantity, unit);
                ingredientAdapter.addItem(ingredient);


            }
        }
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

    private static int REQUEST_CODE_NEW_INGREDIENT = 1;
}