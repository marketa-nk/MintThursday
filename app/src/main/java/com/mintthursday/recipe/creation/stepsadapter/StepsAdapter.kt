package com.mintthursday.recipe.creation.stepsadapter

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mintthursday.R
import com.mintthursday.models.Step
import com.mintthursday.recipe.creation.stepsadapter.RecyclerRowMoveCallback.RecyclerRowMoveTouchHelper
import com.mintthursday.recipe.creation.stepsadapter.StepsAdapter.StepsViewHolder
import java.util.*

class StepsAdapter : RecyclerView.Adapter<StepsViewHolder>(), RecyclerRowMoveTouchHelper {

    var items: MutableList<Step> = mutableListOf()
        private set

    fun setItems(items: List<Step>) {
        this.items = items.toMutableList()
        notifyItemRangeInserted(0, items.size)
    }

    fun addItem(step: Step) {
        items.add(step)
        notifyItemInserted(items.size)
    }

    fun deleteItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_step, parent, false)
        return StepsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onRowMoved(from: Int, to: Int) {
        if (from < to) {
            for (i in from until to) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in from downTo to + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(from, to)
    }

    override fun onRowSelected(stepsViewHolder: StepsViewHolder?) {
        stepsViewHolder?.itemView?.setBackgroundColor(Color.GRAY)
    }

    override fun onClearRow(stepsViewHolder: StepsViewHolder?) {
        stepsViewHolder?.itemView?.setBackgroundColor(Color.WHITE)
    }

    inner class StepsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val stepInstr: TextView = itemView.findViewById(R.id.step)
        private val handle: ImageView = itemView.findViewById(R.id.handle)
        private val btnStepRemove: ImageButton = itemView.findViewById(R.id.step_remove)

        fun bind(step: Step) {
            stepInstr.text = step.stepInstruction
            stepInstr.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    items[adapterPosition].stepInstruction = s.toString().trim { it <= ' ' }
                }
            })
        }

        init {
            btnStepRemove.setOnClickListener() {
                deleteItem(adapterPosition)
            }
        }

    }
}