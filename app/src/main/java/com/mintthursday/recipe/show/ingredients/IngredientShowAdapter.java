package com.mintthursday.recipe.show.ingredients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mintthursday.models.Ingredient;
import com.mintthursday.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IngredientShowAdapter extends RecyclerView.Adapter<IngredientShowAdapter.IngredientShowViewHolder> {

    private List<Ingredient> ingredientShowList = new ArrayList<>();
//    @NonNull
//    private OnItemCheckListener onItemClick;

//    interface OnItemCheckListener {
//        void onItemCheck(Ingredient ingredient);
//        void onItemUncheck(Ingredient ingredient);
//    }
//    public IngredientShowAdapter (List<Ingredient> ingredients, @NonNull OnItemCheckListener onItemCheckListener) {
//        this.ingredientShowList = ingredients;
//        this.onItemClick = onItemCheckListener;
//    }

    @NonNull
    @Override
    public IngredientShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_ingredient_show, parent, false);
        return new IngredientShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientShowViewHolder holder, int position) {
//        final Ingredient currentIngredient = ingredientShowList.get(position);
        holder.bind(ingredientShowList.get(position));

//        holder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.checkbox.setChecked(!holder.checkbox.isChecked());
//                if (holder.checkbox.isChecked()){
//                    onItemClick.onItemCheck(currentIngredient);
//                } else {
//                    onItemClick.onItemUncheck(currentIngredient);
//                }
//            }
//        });

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
//        CheckBox checkbox;

        public IngredientShowViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ingrName = itemView.findViewById(R.id.ingr_name);
            ingrQty = itemView.findViewById(R.id.ingr_qty);
            ingrUnit = itemView.findViewById(R.id.ingr_unit);
//            checkbox = itemView.findViewById(R.id.checkbox);
//            checkbox.setClickable(false);

        }
        public void bind(Ingredient ingredient) {
            ingrName.setText(ingredient.getName());
            ingrQty.setText(String.valueOf(ingredient.getQuantity()));
            ingrUnit.setText(ingredient.getUnit());
        }
//        public void setOnClickListener(View.OnClickListener onClickListener) {
//            itemView.setOnClickListener(onClickListener);
//        }
    }
}
