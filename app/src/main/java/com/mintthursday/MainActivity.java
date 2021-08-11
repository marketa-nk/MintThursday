package com.mintthursday;

import static com.mintthursday.NewRecipeActivity.ARG_RECIPE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_MAIN_ACTIVITY = 1;
    private static final int REQUEST_CODE_EDIT_RECIPE = 2;





    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(this);

        initRecyclerViewMain();
        loadRecipes();
    }

    private void initRecyclerViewMain() {
        RecyclerView recipeRecyclerView = findViewById(R.id.recipe_recycler_view_main);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter();
        recipeRecyclerView.setAdapter(recipeAdapter);
        OnItemClickListenerRecipe a = (new OnItemClickListenerRecipe() {
            @Override
            public void onItemClick(Recipe recipe, int itemPosition) {
                openEditRecipe(recipe);
            }

            @Override
            public void onItemLongClick(Recipe recipe, int position) {
                Toast.makeText(MainActivity.this, "LongClick", Toast.LENGTH_SHORT).show();
            }
        });
        recipeAdapter.setOnItemClickListenerRecipe(a);

    }

    private void openEditRecipe(Recipe recipe) {
        Intent intent = new Intent(this, NewRecipeActivity.class);
        intent.putExtra(ARG_RECIPE, recipe);
        startActivityForResult(intent, REQUEST_CODE_EDIT_RECIPE);
    }

    private void loadRecipes() {
        List<Recipe> recipes = App.getInstance().getDatabase().recipeDao().getAll();
        recipeAdapter.setItems(recipes);
    }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab1:
                Intent intent = new Intent(this, NewRecipeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MAIN_ACTIVITY);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadRecipes();
    }
}



























