package com.mintthursday;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipe")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipe WHERE name = :name")
    Recipe getByRecipe(String name);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Recipe recipe);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);
}
