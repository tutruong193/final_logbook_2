package com.example.final_project_logbook2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<String> taskList;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, this::deleteTask, this::showEditTaskDialog);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        fab.setOnClickListener(v -> showAddTaskDialog());
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Task");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_task, null);
        EditText taskNameEditText = dialogView.findViewById(R.id.taskNameEditText);

        builder.setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String taskName = taskNameEditText.getText().toString().trim();

                    if (taskName.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Task name is empty.", Toast.LENGTH_SHORT).show();
                    } else if (taskList.contains(taskName)) {
                        Toast.makeText(MainActivity.this, "Task already exists.", Toast.LENGTH_SHORT).show();
                    } else {
                        taskList.add(taskName);
                        taskAdapter.notifyItemInserted(taskList.size() - 1);
                        Toast.makeText(MainActivity.this, "Task Created Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showEditTaskDialog(String oldTaskName, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Task");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_task, null);
        EditText taskNameEditText = dialogView.findViewById(R.id.taskNameEditText);
        taskNameEditText.setText(oldTaskName);

        builder.setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String newTaskName = taskNameEditText.getText().toString().trim();
                    if (newTaskName.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Task name is empty.", Toast.LENGTH_SHORT).show();
                    } else if (taskList.contains(newTaskName)) {
                        Toast.makeText(MainActivity.this, "Task already exists.", Toast.LENGTH_SHORT).show();
                    } else {
                        taskList.set(position, newTaskName);
                        taskAdapter.notifyItemChanged(position);
                        Toast.makeText(MainActivity.this, "Updated Successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void deleteTask(String taskName) {
        taskList.remove(taskName);
        taskAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
    }
}
