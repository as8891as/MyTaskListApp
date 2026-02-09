package com.example.mytasklistapp;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) { this.tasks = tasks; }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = tasks.get(position);

        holder.tvTitle.setText(currentTask.getTitle());

        // 1. Set the initial strikethrough state based on the data
        toggleStrikeThrough(holder.tvTitle, currentTask.isDone());

        // 2. Clear listener before setting state to avoid infinite loops or wrong triggers
        holder.cbDone.setOnCheckedChangeListener(null);
        holder.cbDone.setChecked(currentTask.isDone());

        // 3. Re-attach the listener
        holder.cbDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update the data model
            currentTask.setDone(isChecked);
            // Update the UI immediately
            toggleStrikeThrough(holder.tvTitle, isChecked);
        });
    }

    // Helper method to handle the visual crossing out
    private void toggleStrikeThrough(TextView tv, boolean isDone) {
        if (isDone) {
            // Add the strike-through flag
            tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            // Remove the strike-through flag
            tv.setPaintFlags(tv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public int getItemCount() { return tasks.size(); }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        CheckBox cbDone;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTaskTitle);
            cbDone = itemView.findViewById(R.id.cbDone);
        }
    }
}