package com.mintthursday;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity
public class Recipe implements Parcelable {

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

    protected Recipe(Parcel in) {
        name = in.readString();
        description = in.readString();
        countPortion = in.readInt();
        time = in.readInt();
        category = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(countPortion);
        dest.writeInt(time);
        dest.writeString(category);
        dest.writeTypedList(ingredients);
        dest.writeStringList(steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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
