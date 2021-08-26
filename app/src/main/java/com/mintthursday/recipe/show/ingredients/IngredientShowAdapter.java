package com.mintthursday.recipe.show.ingredients;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mintthursday.R;
import com.mintthursday.models.Ingredient;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IngredientShowAdapter extends RecyclerView.Adapter<IngredientShowAdapter.IngredientShowViewHolder> {

    interface OnItemCheckListener {
        void onItemCheck(Ingredient ingredient);
        void onItemUncheck(Ingredient ingredient);
    }

    private List<Ingredient> ingredientShowList;
    private OnItemCheckListener onItemClick;

    public IngredientShowAdapter(List<Ingredient> ingredients, @NonNull OnItemCheckListener onItemCheckListener) {
        this.ingredientShowList = ingredients;
        this.onItemClick = onItemCheckListener;
    }

    @NonNull
    @Override
    public IngredientShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ingredient_show, parent, false);
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

        public CheckBox checkBox;
        private TextView ingrName;
        private TextView ingrQty;
        private TextView ingrUnit;

        public IngredientShowViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ingrName = itemView.findViewById(R.id.ingr_name);
            ingrQty = itemView.findViewById(R.id.ingr_qty);
            ingrUnit = itemView.findViewById(R.id.ingr_unit);
            checkBox = itemView.findViewById(R.id.checkBox);
        }

        public void bind(Ingredient ingredient) {
            ingrName.setText(ingredient.getName());
            ingrQty.setText(String.valueOf(ingredient.getQuantity()));
            ingrUnit.setText(ingredient.getUnit());
            checkBox.setClickable(false);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = !checkBox.isChecked();
                    checkBox.setChecked(checked);
                    if (checked) {
                        onItemClick.onItemCheck(ingredient);
                    } else {
                        onItemClick.onItemUncheck(ingredient);
                    }
                }
            });
        }
    }
}
