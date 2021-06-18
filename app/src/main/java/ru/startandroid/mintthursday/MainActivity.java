package ru.startandroid.mintthursday;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecipeAdapter recipeAdapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab1:
                Intent intent = new Intent(this, NewRecipe.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}



























