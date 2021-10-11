package com.mintthursday.recipe.show.description;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mintthursday.R;
import com.mintthursday.models.Recipe;

public class RecipeDescriptionFragment extends Fragment {
    private static final String ARG_RECIPE_DESCRIPTION = "ARG_RECIPE_DESCRIPTION";
    private Recipe recipe;

    public RecipeDescriptionFragment() {
    }

    public static RecipeDescriptionFragment newInstance(Recipe recipe) {
        RecipeDescriptionFragment fragment = new RecipeDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_RECIPE_DESCRIPTION, recipe);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_description, container, false);
        if (getArguments().getParcelable(ARG_RECIPE_DESCRIPTION) != null) {
            recipe = (Recipe) getArguments().getParcelable(ARG_RECIPE_DESCRIPTION);
            TextView descriptionName = (TextView) view.findViewById(R.id.showDescriptionName);
            TextView descriptionCountPortions = (TextView) view.findViewById(R.id.showDescriptionCountPortions);
            TextView descriptionTime = (TextView) view.findViewById(R.id.showDescriptionTime);
            TextView descriptonDescription = (TextView) view.findViewById(R.id.showDescriptionDescription);
            descriptionName.setText(recipe.getName());
            descriptionCountPortions.setText("Количество порций: " + recipe.getCountPortion());
            descriptionTime.setText("Время приготовления: " + recipe.getTime() + "минут");
            descriptonDescription.setText("Описание: " + recipe.getDescription());
        }
        return view;
    }
}