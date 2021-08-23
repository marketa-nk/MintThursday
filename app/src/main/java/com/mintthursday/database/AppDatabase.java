package com.mintthursday.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mintthursday.models.Recipe;

@Database(entities = {Recipe.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
}
