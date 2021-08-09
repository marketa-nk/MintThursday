package com.mintthursday;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity
public class Recipe {

    @NonNull
    @PrimaryKey
    private String name;
    private String description;
    private int countPortion;
    private int time;
    private String category;
    @TypeConverters({ListIngredientConverter.class})
    private List<Ingredient> ingredients;
    @TypeConverters({ListStringConverter.class})
    private List<String> steps;

    @Ignore
    public Recipe(String name) {
        this.name = name;
    }

    public Recipe(String name, String description, int countPortion, int time, String category, List<Ingredient> ingredients, List<String> steps) {
        this.name = name;
        this.description = description;
        this.countPortion = countPortion;
        this.time = time;
        this.category = category;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getDescription() {
        return description;
    }

    public int getCountPortion() {
        return countPortion;
    }

    public int getTime() {
        return time;
    }

    public String getCategory() {
        return category;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", countPortion=" + countPortion +
                ", time=" + time +
                ", category='" + category + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
