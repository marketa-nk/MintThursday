package com.mintthursday;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListIngredientConverter {
    private final Type listType = new TypeToken<List<Ingredient>>() {
    }.getType();

    @TypeConverter
    public String toJson(List<Ingredient> list) {
        return new Gson().toJson(list, listType);
    }

    @TypeConverter
    public List<Ingredient> fromJson(String json) {
        return new Gson().fromJson(json, listType);

    }
}
