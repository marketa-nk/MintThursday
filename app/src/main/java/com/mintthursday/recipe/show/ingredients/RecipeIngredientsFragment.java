package com.mintthursday.recipe.show.ingredients;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mintthursday.DecimalDigitsInputFilter;
import com.mintthursday.R;
import com.mintthursday.models.Ingredient;
import com.mintthursday.models.Recipe;

import java.util.ArrayList;
import java.util.List;


public class RecipeIngredientsFragment extends Fragment {

    private static final String ARG_RECIPE_INGREDIENTS = "ARG_RECIPE_INGREDIENTS";
    private final List<Ingredient> currentSelectedIngredients = new ArrayList<>();
    private double count;
    private Recipe recipe;
    private List<Ingredient> ingredientList;
    private RecyclerView recyclerView;
    private IngredientShowAdapter ingredientShowAdapter;
    private ImageButton btnMinus;
    private ImageButton btnPlus;
    private Button btnAddToCart;
    private EditText countPortions;
    private final TextWatcher countTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String a = s.toString().trim();
            if (!a.isEmpty()) {
                if (a.startsWith(".")) {
                    a = "0" + a;
                    countPortions.setText(a);
                    countPortions.setSelection(countPortions.getText().length());
                }
                double count = Double.parseDouble(a);
                if (count > 0) {
                    List<Ingredient> ingredientList = recipe.getIngredients();
                    List<Ingredient> listNew = new ArrayList<>();
                    for (int i = 0; i < ingredientList.size(); i++) {
                        double qty = recipe.getIngredients().get(i).getQuantity() / recipe.getCountPortion() * count;
                        Ingredient newIngredient = new Ingredient(ingredientList.get(i).getName(), qty, ingredientList.get(i).getUnit());
                        listNew.add(newIngredient);
                        if (!currentSelectedIngredients.isEmpty()) {
                            for (int j = 0; j < currentSelectedIngredients.size(); j++) {
                                if (newIngredient.getName().equals(currentSelectedIngredients.get(j).getName())) {
                                    currentSelectedIngredients.set(j, newIngredient);
                                    Log.i("Mint", currentSelectedIngredients.toString());
                                    break;
                                }
                            }
                        }
                    }
                    ingredientShowAdapter.setItems(listNew);
                    RecipeIngredientsFragment.this.count = count;
                }
            }
        }
    };
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.minus:
                    if (count > 1) {
                        count--;
                        countPortions.setText(String.valueOf(count));
                    }
                    break;
                case R.id.plus:
                    count++;
                    countPortions.setText(String.valueOf(count));
                    break;
                default:
                    break;
            }
        }
    };

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

        btnMinus = view.findViewById(R.id.minus);
        btnPlus = view.findViewById(R.id.plus);
        btnAddToCart = view.findViewById(R.id.addToCart);
        countPortions = view.findViewById(R.id.count);

        btnMinus.setOnClickListener(onClickListener);
        btnPlus.setOnClickListener(onClickListener);
        countPortions.addTextChangedListener(countTextWatcher);
        countPortions.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});

        recipe = getArguments().getParcelable(ARG_RECIPE_INGREDIENTS);
        ingredientList = recipe.getIngredients();

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientShowAdapter = new IngredientShowAdapter(ingredientList, new IngredientShowAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Ingredient ingredient) {
                currentSelectedIngredients.add(ingredient);
                btnAddToCart.setEnabled(true);
                Log.i("Mint", currentSelectedIngredients.toString());
            }

            @Override
            public void onItemUncheck(Ingredient ingredient) {
                currentSelectedIngredients.remove(ingredient);
                if (currentSelectedIngredients.isEmpty()){
                    btnAddToCart.setEnabled(false);
                }
                Log.i("Mint", currentSelectedIngredients.toString());
            }
        });

        countPortions.setText(String.valueOf(recipe.getCountPortion()));
        count = recipe.getCountPortion();

        recyclerView.setAdapter(ingredientShowAdapter);

        return view;
    }
}