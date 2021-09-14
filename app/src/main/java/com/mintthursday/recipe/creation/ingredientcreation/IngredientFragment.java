package com.mintthursday.recipe.creation.ingredientcreation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.mintthursday.R;
import com.mintthursday.models.Ingredient;

import java.util.Objects;

public class IngredientFragment extends Fragment {

    public static final String ARG_INGREDIENT = "ARG_INGREDIENT";
    public static final String ARG_POSITION = "ARG_POSITION";
    public static final String BUN_INGREDIENT = "BUN_INGREDIENT";
    public static final String BUN_ITEM_POSITION = "BUN_ITEM_POSITION";
    public static final String REQ_INGREDIENT = "REQ_INGREDIENT";

    private Button btnSave;
    private EditText ingrQty;
    private EditText ingrName;
    private MaterialAutoCompleteTextView ingrUnit;
    private final TextWatcher ingrTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String ingrInput = ingrName.getText().toString().trim();
            String qtyInput = ingrQty.getText().toString().trim();
            String unitInput = ingrUnit.getText().toString().trim();
            btnSave.setEnabled(!ingrInput.isEmpty() && !qtyInput.isEmpty() && !unitInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private int itemPosition = -1;

    public IngredientFragment() {
    }

    public static IngredientFragment newInstance(Ingredient ingredient, int itemPosition) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_INGREDIENT, ingredient);
        bundle.putInt(ARG_POSITION, itemPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        initToolbar(view);

        btnSave = view.findViewById(R.id.btnSave);
        ingrQty = view.findViewById(R.id.textInputEditTextQty);
        ingrName = view.findViewById(R.id.textInputEditTextName);
        ingrUnit = view.findViewById(R.id.spinnerIngredUnits);

        ingrName.addTextChangedListener(ingrTextWatcher);
        ingrQty.addTextChangedListener(ingrTextWatcher);
        ingrUnit.addTextChangedListener(ingrTextWatcher);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIngredient();
            }
        });

        String[] type = getResources().getStringArray(R.array.ingredient_units_list);
        ingrUnit.setAdapter(new ArrayAdapter<>(this.getContext(), R.layout.dropdown_menu_ingredient_unit, type));
        if (getArguments() != null && getArguments().getParcelable(ARG_INGREDIENT) != null) {
            initEditIngredient();
        }
        return view;
    }

    private void initToolbar(View view) {
        Toolbar ingredientToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(ingredientToolbar);
        ingredientToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) getActivity()).onBackPressed();
            }
        });
    }

    private void initEditIngredient() {
        Ingredient ingredientEdit = (Ingredient) getArguments().getParcelable(ARG_INGREDIENT);
        itemPosition = getArguments().getInt(ARG_POSITION);
        if (ingredientEdit != null) {
            ingrName.setText(ingredientEdit.getName());
            ingrQty.setText(String.valueOf(ingredientEdit.getQuantity()));
            ingrUnit.setText(ingredientEdit.getUnit());
        }
    }

    private void saveIngredient() {
        String name = String.valueOf(ingrName.getText());
        String stringQuantity = String.valueOf(ingrQty.getText());
        double quantity;
        if (!stringQuantity.isEmpty()) {
            quantity = Double.parseDouble(stringQuantity);
        } else {
            quantity = 0;
        }
        String unit = String.valueOf(ingrUnit.getText());
        Bundle result = new Bundle();
        Ingredient ingredient = new Ingredient(name, quantity, unit);
        result.putParcelable(BUN_INGREDIENT, ingredient);
        result.putInt(BUN_ITEM_POSITION, itemPosition);
        getParentFragmentManager().setFragmentResult(REQ_INGREDIENT, result);
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }
}