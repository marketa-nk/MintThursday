package ru.startandroid.mintthursday;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NewRecipeActivity extends AppCompatActivity implements CategoryFragment.NoticeDialogListener {

    private StepsAdapter stepsAdapter;
    private TextView editCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        ImageView closeWindow = findViewById(R.id.close);


        editCategories = findViewById(R.id.categories);
        View butCattegory = findViewById(R.id.outlinedInputCategory);
        View butIngredient = findViewById(R.id.ingredientmin);

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
                startActivity(intent);

            }
        });
        closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void initRecyclerViewSteps() {
        RecyclerView stepsRecyclerView = findViewById(R.id.steps_recycler_view);
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
            list.add(new Step("Напишите инструкцию… " + i));
        }

        return list;
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