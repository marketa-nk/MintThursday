package com.mintthursday;

import static com.mintthursday.MainActivity.ARG_SHOW_RECIPE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class ShowRecipeActivity extends AppCompatActivity {
    private SampleFragmentPagerAdapter tabAdapter;

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

        tabAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(), ShowRecipeActivity.this);
        tabAdapter.addFragment(fragmentDescription, "Описание");
        tabAdapter.addFragment(fragmentIngredients, "Ингредиенты");
        tabAdapter.addFragment(fragmentSteps, "Приготовление");
        viewPager.setAdapter(tabAdapter);
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