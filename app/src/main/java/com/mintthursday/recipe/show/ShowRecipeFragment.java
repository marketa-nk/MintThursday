package com.mintthursday.recipe.show;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.mintthursday.R;
import com.mintthursday.models.Recipe;
import com.mintthursday.recipe.show.description.RecipeDescriptionFragment;
import com.mintthursday.recipe.show.ingredients.RecipeIngredientsFragment;
import com.mintthursday.recipe.show.steps.RecipeStepsFragment;

public class ShowRecipeFragment extends Fragment {

    private static final String ARG_RECIPE = "ARG_RECIPE";
    private Recipe recipe;

    private RecipePagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ShowRecipeFragment() {
    }

    public static ShowRecipeFragment newInstance(Recipe recipe) {
        ShowRecipeFragment fragment = new ShowRecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_RECIPE, recipe);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_recipe, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        Toolbar ingredientToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(ingredientToolbar);
        ingredientToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).onBackPressed();
            }
        });
        recipe = (Recipe) getArguments().getParcelable(ARG_RECIPE);
        Fragment fragmentDescription = RecipeDescriptionFragment.newInstance(recipe);
        Fragment fragmentIngredients = RecipeIngredientsFragment.newInstance(recipe);
        Fragment fragmentSteps = RecipeStepsFragment.newInstance(recipe);

        pagerAdapter = new RecipePagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(fragmentDescription, getResources().getString(R.string.description));
        pagerAdapter.addFragment(fragmentIngredients, getResources().getString(R.string.ingredients));
        pagerAdapter.addFragment(fragmentSteps, getResources().getString(R.string.cook));
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}