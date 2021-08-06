package com.mintthursday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<Ingredient> ingredientList = new ArrayList<>();
    OnItemClickListener ingredientListener;

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.ingredientListener = itemClickListener;
    }

    public List<Ingredient> getItems() {
        return ingredientList;
    }

    public void addItem(Ingredient ingredient) {
        ingredientList.add(ingredient);
        notifyItemInserted(ingredientList.indexOf(ingredient));

    }

    private void deleteItem(int position) {
        ingredientList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateItem(Ingredient ingredient, int itemPosition) {
        ingredientList.set(itemPosition,ingredient);
        notifyItemChanged(itemPosition);
    }

    @NonNull
    @NotNull
    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.bind(ingredientList.get(position));

    }
    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
    class IngredientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView ingrName;
        private TextView ingrQty;

        private TextView ingrUnit;

        private ImageButton btnIngrRemove;


        public IngredientViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ingrName = itemView.findViewById(R.id.ingr_name);
            ingrQty = itemView.findViewById(R.id.ingr_qty);
            ingrUnit = itemView.findViewById(R.id.ingr_unit);
            btnIngrRemove = itemView.findViewById(R.id.ingr_remove);
            btnIngrRemove.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        public void bind(Ingredient ingredient) {
            ingrName.setText(ingredient.getName());
            ingrQty.setText(String.valueOf(ingredient.getQuantity()));
            ingrUnit.setText(ingredient.getUnit());

        }

        @Override
        public void onClick(View v) {
            if (v.equals(btnIngrRemove)) {
                deleteItem(getAdapterPosition());
            } else {
                if (ingredientListener != null) {
                    Ingredient ingredient = ingredientList.get(getAdapterPosition());
                    int i = getAdapterPosition();
                    ingredientListener.onItemClick(ingredient,i);
                }
            }
        }

    }


}
