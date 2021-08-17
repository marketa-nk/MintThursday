package com.mintthursday;

public interface OnItemClickListenerRecipe {
    void onItemClick(Recipe recipe, int position);
    boolean onItemLongClick(Recipe recipe, int position);
}
