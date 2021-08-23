package com.mintthursday.recipelist;

import static com.mintthursday.recipe.creation.NewRecipeActivity.ARG_RECIPE;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mintthursday.App;
import com.mintthursday.recipe.creation.NewRecipeActivity;
import com.mintthursday.R;
import com.mintthursday.models.Recipe;
import com.mintthursday.recipe.show.ShowRecipeActivity;

import java.util.List;

public class RecipeListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_MAIN_ACTIVITY = 1;
    private static final int REQUEST_CODE_EDIT_RECIPE = 2;
    public static final String ARG_SHOW_RECIPE = "ARG_SHOW_RECIPE";

    private androidx.appcompat.view.ActionMode actionMode;

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
        OnRecipeClickListener a = (new OnRecipeClickListener() {
            @Override
            public void onItemClick(Recipe recipe, int itemPosition) {
                showRecipe(recipe);
            }

            @Override
            public boolean onItemLongClick(Recipe recipe, int position) {
                if (actionMode != null) {
                    return false;
                }
                actionMode = startSupportActionMode(new androidx.appcompat.view.ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(androidx.appcompat.view.ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.menu_main_activity, menu);
                        mode.setTitle("Hi");
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(androidx.appcompat.view.ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(androidx.appcompat.view.ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.correct_recipe:
                                openEditRecipe(recipe);
                                mode.finish();
                                return true;
                            case R.id.delete_recipe:
                                App.getInstance().getDatabase().recipeDao().delete(recipe);
                                loadRecipes();
                                mode.finish();
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onDestroyActionMode(androidx.appcompat.view.ActionMode mode) {
                        actionMode = null;

                    }
                });
                return true;
                //Toast.makeText(MainActivity.this, "LongClick", Toast.LENGTH_SHORT).show();

            }
        });
        recipeAdapter.setOnItemClickListenerRecipe(a);

    }

    private void showRecipe(Recipe recipe) {
        Intent intent = new Intent(RecipeListActivity.this, ShowRecipeActivity.class);
        intent.putExtra(ARG_SHOW_RECIPE, recipe);
        startActivity(intent);

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



























