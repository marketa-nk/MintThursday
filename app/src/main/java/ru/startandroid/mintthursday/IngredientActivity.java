package ru.startandroid.mintthursday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class IngredientActivity extends AppCompatActivity {

    public static String INTENT_RESULT_ARG_NAME = "name";
    public static String INTENT_RESULT_ARG_QUANTITY = "quantity";
    public static String INTENT_RESULT_ARG_UNIT = "unit";
    Button btnSave;
    EditText ingrQty;
    EditText ingrName;
    MaterialAutoCompleteTextView ingrUnit;

    private final TextWatcher ingrTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String ingrInput =ingrName.getText().toString().trim();
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
                intent.putExtra(INTENT_RESULT_ARG_NAME, name);
                intent.putExtra(INTENT_RESULT_ARG_QUANTITY, quantity);
                intent.putExtra(INTENT_RESULT_ARG_UNIT, unit);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        Toolbar ingredientToolbar = (Toolbar) findViewById(R.id.topAppBar);
        setSupportActionBar(ingredientToolbar);
        ingredientToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String[] type = getResources().getStringArray(R.array.ingredientUnits);
        ingrUnit.setAdapter(new ArrayAdapter<>(this, R.layout.dropdown_menu_ingredient_unit, type));

    }


}