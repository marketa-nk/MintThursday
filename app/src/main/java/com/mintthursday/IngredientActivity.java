package com.mintthursday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class IngredientActivity extends AppCompatActivity {

    public static final String ARG_INGREDIENT = "ARG_INGREDIENT";
    public static final String ARG_POSITION = "ARG_POSITION";

    public static final String INTENT_RESULT_ARG_NAME = "name";
    public static final String INTENT_RESULT_ARG_QUANTITY = "quantity";
    public static final String INTENT_RESULT_ARG_UNIT = "unit";
    public static final String INTENT_RESULT_ARG_POSITION = "position";

    private Button btnSave;
    private EditText ingrQty;
    private EditText ingrName;
    private MaterialAutoCompleteTextView ingrUnit;

    private Integer itemPosition;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        initToolbar();

        btnSave = findViewById(R.id.btnSave);
        ingrQty = findViewById(R.id.textInputEditTextQty);
        ingrName = findViewById(R.id.textInputEditTextName);
        ingrUnit = findViewById(R.id.spinnerIngredUnits);

        ingrName.addTextChangedListener(ingrTextWatcher);
        ingrQty.addTextChangedListener(ingrTextWatcher);
        ingrUnit.addTextChangedListener(ingrTextWatcher);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIngredient();
            }
        });

        String[] type = getResources().getStringArray(R.array.ingredientUnits);
        ingrUnit.setAdapter(new ArrayAdapter<>(this, R.layout.dropdown_menu_ingredient_unit, type));

        initEditIngredient();
    }

    private void initToolbar() {
        Toolbar ingredientToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(ingredientToolbar);
        ingredientToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initEditIngredient() {
        Ingredient ingredientEdit = getIntent().getParcelableExtra(ARG_INGREDIENT);
        itemPosition = (Integer) getIntent().getSerializableExtra(ARG_POSITION);
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

        Intent intent = new Intent();
        if (itemPosition != null) {
            intent.putExtra(INTENT_RESULT_ARG_POSITION, itemPosition);
        }
        intent.putExtra(INTENT_RESULT_ARG_NAME, name);
        intent.putExtra(INTENT_RESULT_ARG_QUANTITY, quantity);
        intent.putExtra(INTENT_RESULT_ARG_UNIT, unit);
        setResult(RESULT_OK, intent);
        finish();

    }
}