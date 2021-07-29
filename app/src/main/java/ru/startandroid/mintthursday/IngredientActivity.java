package ru.startandroid.mintthursday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        Toolbar ingredientToolbar = (Toolbar) findViewById(R.id.topAppBar);
        setSupportActionBar(ingredientToolbar);
        ingredientToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

    }


}