package com.mintthursday;

import static androidx.room.OnConflictStrategy.*;

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

    @Query("SELECT * FROM recipe WHERE id = :id")
    Recipe getById(long id);

    @Insert(onConflict = REPLACE)
    void insert(Recipe recipe);

    @Delete
    void delete(Recipe recipe);
}
