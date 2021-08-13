package com.mintthursday;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> implements RecyclerRowMoveCallback.RecyclerRowMoveTouchHelper {
    private List<Step> stepsList = new ArrayList<>();

    public void setItems(List<Step> steps) {
        stepsList = steps;
        notifyDataSetChanged();
    }

    public void addItem(Step step) {
        stepsList.add(step);
        notifyDataSetChanged();
    }

    public void clearItems() {
        stepsList.clear();
        notifyDataSetChanged();
    }
    public List<Step> getItems(){
        return stepsList;
    }


    @NonNull
    @NotNull
    @Override
    public StepsAdapter.StepsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_step, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StepsAdapter.StepsViewHolder holder, int position) {
        holder.bind(stepsList.get(position));
        holder.stepInstr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int i = holder.getAdapterPosition();
                stepsList.get(i).setStepInstruction(s.toString().trim());
            }
        });

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    @Override
    public void onRowMoved(int from, int to) {
        if (from < to) {
            for (int i = from; i < to; i++) {
                Collections.swap(stepsList, i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--) {
                Collections.swap(stepsList, i, i - 1);
            }
        }
        notifyItemMoved(from, to);
    }

    @Override
    public void onRowSelected(StepsViewHolder stepsViewHolder) {
        stepsViewHolder.itemView.setBackgroundColor(Color.GRAY);

    }

    @Override
    public void onClearRow(StepsViewHolder stepsViewHolder) {
        stepsViewHolder.itemView.setBackgroundColor(Color.WHITE);

    }

    class StepsViewHolder extends RecyclerView.ViewHolder {
        private TextView stepInstr;
        private ImageView handle;

        public StepsViewHolder(View itemView) {
            super(itemView);
            stepInstr = itemView.findViewById(R.id.step);
            handle = itemView.findViewById(R.id.handle);
        }


        public void bind(Step step) {
            stepInstr.setText(step.getStepInstruction());

        }

    }
}

