package ru.startandroid.mintthursday;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> implements RecyclerRowMoveCallback.RecyclerRowMoveTouchHelper{
    private List<Step> stepsList = new ArrayList<>();
    private StepsViewHolder holder;
    private int position;

    public void setItems(List<Step> steps) {
        stepsList.addAll(steps);
        notifyDataSetChanged();
    }
    public void clearItems() {
        stepsList.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public StepsAdapter.StepsViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_step, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull StepsAdapter.StepsViewHolder holder, int position) {
        this.holder = holder;
        this.position = position;
        holder.bind(stepsList.get(position));

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    @Override
    public void onRowMoved(int from, int to) {
        if (from < to){
            for (int i = from; i < to; i++){
                Collections.swap(stepsList, i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--){
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

    class StepsViewHolder extends RecyclerView.ViewHolder{
        private TextView stepInstr;
        private ImageView handle;

        public StepsViewHolder(View itemView) {
            super(itemView);
            stepInstr = itemView.findViewById(R.id.step);
            handle = itemView.findViewById(R.id.handle);

        }


        public void bind(Step step) {
            stepInstr.setHint(step.getStepInstruction());

        }

    }
}

