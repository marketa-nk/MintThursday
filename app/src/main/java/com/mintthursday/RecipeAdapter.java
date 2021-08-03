package com.mintthursday;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipeList = new ArrayList<>();

    public void setItems(List<Recipe> recipes) {
        recipeList.addAll(recipes);
        notifyDataSetChanged();
    }
    public void clearItems() {
        recipeList.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private ImageView recipeImageView;
        private TextView recipeName;


        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeImageView = itemView.findViewById(R.id.recipe_image_view);
            recipeName = itemView.findViewById(R.id.recipe_name);
        }


        public void bind(Recipe recipe) {
            recipeName.setText(recipe.getName());
            recipeImageView.setImageDrawable(AppCompatResources.getDrawable(recipeImageView.getContext(), R.drawable.img_food));
        }
    }

}

