package com.example.mytasklistapp;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
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

        // 1. Set Title and Strikethrough
        holder.tvTitle.setText(currentTask.getTitle());
        toggleStrikeThrough(holder.tvTitle, currentTask.isDone());

        // 2. Handle Checkbox
        holder.cbDone.setOnCheckedChangeListener(null);
        holder.cbDone.setChecked(currentTask.isDone());
        holder.cbDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentTask.setDone(isChecked);
            toggleStrikeThrough(holder.tvTitle, isChecked);
        });

        // 3. Handle Delete Button (NEW)
        holder.btnDelete.setOnClickListener(v -> {
            // Get the current position of the item
            int currentPos = holder.getAdapterPosition();

            // Check if position is valid
            if (currentPos != RecyclerView.NO_POSITION) {
                // Remove from the list
                tasks.remove(currentPos);
                // Notify the adapter to animate the removal
                notifyItemRemoved(currentPos);
                // Notify the adapter that the range changed (updates positions for items below)
                notifyItemRangeChanged(currentPos, tasks.size());
            }
        });
    }

    private void toggleStrikeThrough(TextView tv, boolean isDone) {
        if (isDone) {
            tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tv.setPaintFlags(tv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public int getItemCount() { return tasks.size(); }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        CheckBox cbDone;
        ImageButton btnDelete; // New Button

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTaskTitle);
            cbDone = itemView.findViewById(R.id.cbDone);
            btnDelete = itemView.findViewById(R.id.btnDelete); // Find the new button
        }
    }
}