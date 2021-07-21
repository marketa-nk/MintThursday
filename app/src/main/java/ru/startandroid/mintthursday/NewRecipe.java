package ru.startandroid.mintthursday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class NewRecipe extends AppCompatActivity{

    private StepsAdapter stepsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        initRecyclerViewSteps();
        loadSteps();
    }

    private void initRecyclerViewSteps(){
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
            list.add(new Step("Напишите инструкцию… "+i));
        }

        return list;
    }

}