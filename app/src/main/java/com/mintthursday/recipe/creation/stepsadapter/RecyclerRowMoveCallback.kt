package com.mintthursday.recipe.creation.stepsadapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mintthursday.recipe.creation.stepsadapter.StepsAdapter.StepsViewHolder

class RecyclerRowMoveCallback(private val recyclerRowMoveTouchHelper: RecyclerRowMoveTouchHelper) : ItemTouchHelper.Callback() {
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlag, 0)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        recyclerRowMoveTouchHelper.onRowMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is StepsViewHolder) {
                recyclerRowMoveTouchHelper.onRowSelected(viewHolder)
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is StepsViewHolder) {
            recyclerRowMoveTouchHelper.onClearRow(viewHolder)
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
    interface RecyclerRowMoveTouchHelper {
        fun onRowMoved(from: Int, to: Int)
        fun onRowSelected(stepsViewHolder: StepsViewHolder?)
        fun onClearRow(stepsViewHolder: StepsViewHolder?)
    }
}