package com.example.final_project_logbook2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<String> taskList;
    private final OnDeleteClickListener onDeleteClickListener;
    private final OnEditClickListener onEditClickListener;

    public TaskAdapter(List<String> taskList, OnDeleteClickListener onDeleteClickListener, OnEditClickListener onEditClickListener) {
        this.taskList = taskList;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onEditClickListener = onEditClickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        String task = taskList.get(position);
        holder.taskNameTextView.setText(task);

        holder.deleteIcon.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(task));
        holder.taskNameTextView.setOnClickListener(v -> onEditClickListener.onEditClick(task, position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String taskName);
    }

    public interface OnEditClickListener {
        void onEditClick(String taskName, int position);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskNameTextView;
        ImageView deleteIcon;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.task_name);
            deleteIcon = itemView.findViewById(R.id.delete_icon);
        }
    }
}
