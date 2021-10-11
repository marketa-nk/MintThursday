package com.mintthursday.recipe.show.steps

import androidx.recyclerview.widget.RecyclerView
import com.mintthursday.recipe.show.steps.StepsShowAdapter.StepsShowViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.mintthursday.R
import android.widget.TextView
import com.mintthursday.models.Step
import java.util.ArrayList

class StepsShowAdapter : RecyclerView.Adapter<StepsShowViewHolder>() {
    private var items: List<Step> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsShowViewHolder {
        return StepsShowViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_step_show, parent, false))
    }

    fun setItems(steps: List<Step>) {
        items = steps
        notifyItemRangeInserted(0, items.size)
    }

    override fun onBindViewHolder(holder: StepsShowViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class StepsShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.step_show)
        fun bind(step: Step) {
            textView.text = step.stepInstruction
        }

    }
}