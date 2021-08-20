package com.mintthursday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IngredientShowAdapter extends RecyclerView.Adapter<IngredientShowAdapter.IngredientShowViewHolder> {

    OnItemClickListenerIngredient ingredientShowListener;
    private List<Ingredient> ingredientShowList = new ArrayList<>();

    @NonNull
    @Override
    public IngredientShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_ingredient_show, parent, false);
        return new IngredientShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientShowViewHolder holder, int position) {
        holder.bind(ingredientShowList.get(position));

    }

    @Override
    public int getItemCount() {
        return ingredientShowList.size();
    }

    public void setItems(List<Ingredient> ingredients) {
        ingredientShowList = ingredients;
        notifyDataSetChanged();
    }

    public class IngredientShowViewHolder extends RecyclerView.ViewHolder {

        private TextView ingrName;
        private TextView ingrQty;
        private TextView ingrUnit;

        public IngredientShowViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ingrName = itemView.findViewById(R.id.ingr_name);
            ingrQty = itemView.findViewById(R.id.ingr_qty);
            ingrUnit = itemView.findViewById(R.id.ingr_unit);

        }
        public void bind(Ingredient ingredient) {
            ingrName.setText(ingredient.getName());
            ingrQty.setText(String.valueOf(ingredient.getQuantity()));
            ingrUnit.setText(ingredient.getUnit());
        }


    }
}
