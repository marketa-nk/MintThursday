package com.mintthursday.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mintthursday.models.Ingredient

class ListIngredientConverter {

    private val listType = object : TypeToken<List<Ingredient>>() {}.type

    @TypeConverter
    fun toJson(list: List<Ingredient>): String {
        return Gson().toJson(list, listType)
    }

    @TypeConverter
    fun fromJson(json: String): List<Ingredient> {
        return Gson().fromJson(json, listType)
    }
}
