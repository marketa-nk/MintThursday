package ru.startandroid.mintthursday;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        initRecyclerView();
        loadRecipes();
    }

    private void initRecyclerView() {
        RecyclerView recipeRecyclerView = findViewById(R.id.recipe_recycler_view);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter();
        recipeRecyclerView.setAdapter(recipeAdapter);
    }

    private void loadRecipes() {
        List<Recipe> recipes = getRecipes();
        recipeAdapter.setItems(recipes);
    }

    private List<Recipe> getRecipes() {
        List<Recipe> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new Recipe("Recipe " + i ));
        }

        return list;
    }
}



























