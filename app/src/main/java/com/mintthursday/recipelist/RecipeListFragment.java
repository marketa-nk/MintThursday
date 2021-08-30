package com.mintthursday.recipelist;

import static com.mintthursday.recipe.creation.NewRecipeActivity.ARG_RECIPE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mintthursday.App;
import com.mintthursday.recipe.creation.NewRecipeActivity;
import com.mintthursday.R;
import com.mintthursday.models.Recipe;
import com.mintthursday.recipe.show.ShowRecipeActivity;

import java.util.List;

public class RecipeListFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CODE_MAIN_ACTIVITY = 1;
    private static final int REQUEST_CODE_EDIT_RECIPE = 2;
    public static final String ARG_SHOW_RECIPE = "ARG_SHOW_RECIPE";

    private androidx.appcompat.view.ActionMode actionMode;

    private RecipeAdapter recipeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        Toolbar myToolbar = view.findViewById(R.id.myToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        FloatingActionButton fab = view.findViewById(R.id.fab1);
        fab.setOnClickListener(this);

        initRecyclerViewMain(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadRecipes();
    }

    private void initRecyclerViewMain(View view) {
        RecyclerView recipeRecyclerView = view.findViewById(R.id.recipe_recycler_view_main);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                actionMode =  ((AppCompatActivity) getActivity()).startSupportActionMode(new androidx.appcompat.view.ActionMode.Callback() {
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

            }
        });
        recipeAdapter.setOnItemClickListenerRecipe(a);

    }

    private void showRecipe(Recipe recipe) {
        Intent intent = new Intent(getContext(), ShowRecipeActivity.class);
        intent.putExtra(ARG_SHOW_RECIPE, recipe);
        startActivity(intent);

    }

    private void openEditRecipe(Recipe recipe) {
        Intent intent = new Intent(getContext(), NewRecipeActivity.class);
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
                Intent intent = new Intent(getContext(), NewRecipeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MAIN_ACTIVITY);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadRecipes();
    }
}



























