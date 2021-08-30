package com.mintthursday;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mintthursday.recipelist.RecipeListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new RecipeListFragment()).commit();


    }
}