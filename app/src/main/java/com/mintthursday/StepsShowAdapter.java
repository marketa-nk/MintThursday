package com.mintthursday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StepsShowAdapter extends RecyclerView.Adapter<StepsShowAdapter.StepsShowViewHolder>{
    private List<Step> stepsList = new ArrayList<>();
    @NonNull
    @Override
    public StepsShowAdapter.StepsShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_step_show, parent, false);
        return new StepsShowViewHolder(view);
    }
    public void setItems(List<Step> steps) {
        stepsList = steps;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull StepsShowAdapter.StepsShowViewHolder holder, int position) {
        holder.bind(stepsList.get(position));
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class StepsShowViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public StepsShowViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.step_show);
        }
        public void bind(Step step) {
            textView.setText(step.getStepInstruction());
        }
    }
}
