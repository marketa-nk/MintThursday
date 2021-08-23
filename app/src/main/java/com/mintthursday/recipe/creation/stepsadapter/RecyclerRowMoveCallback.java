package com.mintthursday.recipe.creation.stepsadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class RecyclerRowMoveCallback extends ItemTouchHelper.Callback {
    private RecyclerRowMoveTouchHelper recyclerRowMoveTouchHelper;

    public RecyclerRowMoveCallback(RecyclerRowMoveTouchHelper recyclerRowMoveTouchHelper) {
        this.recyclerRowMoveTouchHelper = recyclerRowMoveTouchHelper;

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlag, 0);
    }

    @Override
    public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
        this.recyclerRowMoveTouchHelper.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSelectedChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof StepsAdapter.StepsViewHolder) {
                StepsAdapter.StepsViewHolder stepsViewHolder = (StepsAdapter.StepsViewHolder) viewHolder;
                recyclerRowMoveTouchHelper.onRowSelected(stepsViewHolder);
            }
        }


        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof StepsAdapter.StepsViewHolder) {
            StepsAdapter.StepsViewHolder stepsViewHolder = (StepsAdapter.StepsViewHolder) viewHolder;
            recyclerRowMoveTouchHelper.onClearRow(stepsViewHolder);
        }
    }

    @Override
    public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    public interface RecyclerRowMoveTouchHelper {
        void onRowMoved(int from, int to);

        void onRowSelected(StepsAdapter.StepsViewHolder stepsViewHolder);

        void onClearRow(StepsAdapter.StepsViewHolder stepsViewHolder);
    }
}
