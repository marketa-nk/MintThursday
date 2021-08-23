package com.mintthursday.recipelist;

import com.mintthursday.models.Recipe;

public interface OnRecipeClickListener {
    void onItemClick(Recipe recipe, int position);
    boolean onItemLongClick(Recipe recipe, int position);
}
