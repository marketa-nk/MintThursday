package com.mintthursday;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecipeIngredientsFragment extends Fragment {

    private static final String ARG_RECIPE_INGREDIENTS = "ARG_RECIPE_INGREDIENTS";
    private Recipe recipe;
    private RecyclerView recyclerView;
    private IngredientShowAdapter ingredientShowAdapter;

    public RecipeIngredientsFragment() {
    }

    public static RecipeIngredientsFragment newInstance(Recipe recipe) {
        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_RECIPE_INGREDIENTS, recipe);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientShowAdapter = new IngredientShowAdapter();
        recyclerView.setAdapter(ingredientShowAdapter);
        recipe = (Recipe) getArguments().getParcelable(ARG_RECIPE_INGREDIENTS);
        ingredientShowAdapter.setItems(recipe.getIngredients());

        return view;
    }
}