package com.mintthursday.recipe.show;

import static com.mintthursday.recipelist.RecipeListActivity.ARG_SHOW_RECIPE;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mintthursday.R;
import com.mintthursday.models.Recipe;
import com.mintthursday.recipe.show.description.RecipeDescriptionFragment;
import com.mintthursday.recipe.show.ingredients.RecipeIngredientsFragment;
import com.mintthursday.recipe.show.steps.RecipeStepsFragment;

public class ShowRecipeActivity extends AppCompatActivity {
    private RecipePagerAdapter pagerAdapter;

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);
        initToolbar();

        viewPager = findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        Recipe recipe = getIntent().getParcelableExtra(ARG_SHOW_RECIPE);
        Fragment fragmentDescription = RecipeDescriptionFragment.newInstance(recipe);
        Fragment fragmentIngredients = RecipeIngredientsFragment.newInstance(recipe);
        Fragment fragmentSteps = RecipeStepsFragment.newInstance(recipe);

        pagerAdapter = new RecipePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(fragmentDescription, getResources().getString(R.string.description));
        pagerAdapter.addFragment(fragmentIngredients, getResources().getString(R.string.ingredients));
        pagerAdapter.addFragment(fragmentSteps, getResources().getString(R.string.cook));
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initToolbar() {
        Toolbar ingredientToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(ingredientToolbar);
        ingredientToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


}