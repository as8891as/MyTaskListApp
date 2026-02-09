package com.example.mytasklistapp;

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

        holder.cbDone.setOnCheckedChangeListener(null);
        holder.cbDone.setChecked(currentTask.isDone());

        holder.cbDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentTask.setDone(isChecked);
        });
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