package com.mintthursday.database

import androidx.room.*
import com.mintthursday.models.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(vararg recipe: Recipe)

    @Delete
    fun deleteRecipe(recipe: Recipe?)

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getRecipeById(id: Long): Recipe?

    @Query("SELECT name FROM recipe WHERE id = :id")
    fun getRecipeNameById(id: Long): String
}