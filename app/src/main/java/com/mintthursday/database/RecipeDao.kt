package com.mintthursday.database

import androidx.room.*
import com.mintthursday.models.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe")
    fun getAll(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getById(id: Long): Recipe?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe?)
}