package com.example.buoi7; // Ensure this is the correct package name for your project

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

// Custom ArrayAdapter for ToDo objects
public class ToDoAdapter extends ArrayAdapter<ToDo> {

    private Context context;
    private List<ToDo> tasks;

    // Constructor
    public ToDoAdapter(Context context, List<ToDo> tasks) {
        super(context, 0, tasks); // 0 because we are inflating the custom view ourselves
        this.context = context;
        this.tasks = tasks;
    }

    // Method to get a view for each item in the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ToDo task = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the new view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }

        // Lookup views for data population
        TextView taskTitle = convertView.findViewById(R.id.task_title);
        TextView taskContent = convertView.findViewById(R.id.task_content);
        TextView taskDate = convertView.findViewById(R.id.task_date);
        TextView taskType = convertView.findViewById(R.id.task_type);

        // Populate the data into the template view using the data object
        taskTitle.setText(task.getTitle());
        taskContent.setText(task.getContent());
        taskDate.setText(task.getDate());
        taskType.setText(task.getType());

        // Return the completed view to render on screen
        return convertView;
    }
}
