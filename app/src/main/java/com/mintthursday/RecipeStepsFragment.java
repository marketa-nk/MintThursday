package com.mintthursday;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsFragment extends Fragment {

    private static final String ARG_RECIPE_STEPS = "ARG_RECIPE_STEPS";
    private Recipe recipe;
    private RecyclerView recyclerView;
    private StepsShowAdapter stepsShowAdapter;
    private ImageView handle;

    public RecipeStepsFragment() {
    }

    public static RecipeStepsFragment newInstance(Recipe recipe) {
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_RECIPE_STEPS, recipe);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stepsShowAdapter = new StepsShowAdapter();
        recyclerView.setAdapter(stepsShowAdapter);
        recipe = (Recipe) getArguments().getParcelable(ARG_RECIPE_STEPS);
        List<Step> list = new ArrayList<>();
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            list.add(new Step(recipe.getSteps().get(i)));
        }
        stepsShowAdapter.setItems(list);

        return view;


    }
}