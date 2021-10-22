package com.mintthursday.database

import androidx.room.Database
import androidx.room.TypeConverters
import com.mintthursday.database.converters.ListStringConverter
import com.mintthursday.database.converters.ListIngredientConverter
import com.mintthursday.database.converters.ListStepConverter
import androidx.room.RoomDatabase
import com.mintthursday.models.Purchase
import com.mintthursday.models.Recipe

@Database(entities = [Recipe::class, Purchase::class], version = 4)
@TypeConverters(ListStringConverter::class, ListIngredientConverter::class, ListStepConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun purchaseDao(): PurchaseDao
}