package com.example.buoi7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView todoListView;
    EditText titleInput, contentInput, dateInput, typeInput;
    Button addBtn, updateBtn, deleteBtn;
    ToDoAdapter toDoAdapter;
    todoDAO todoDAO;
    ArrayList<ToDo> list;
    private int selectedToDoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        todoListView = findViewById(R.id.todoListView);
        titleInput = findViewById(R.id.titleInput);
        contentInput = findViewById(R.id.contentInput);
        dateInput = findViewById(R.id.dateInput);
        typeInput = findViewById(R.id.typeInput);
        addBtn = findViewById(R.id.addBtn);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        // Initialize todoDAO and ToDoAdapter
        todoDAO = new todoDAO(this);
        list = todoDAO.getListToDo();
        toDoAdapter = new ToDoAdapter(this, list);
        todoListView.setAdapter(toDoAdapter);
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy mục ToDo được chọn
                ToDo selectedToDo = (ToDo) parent.getItemAtPosition(position);
                // Cập nhật selectedToDoId với ID của mục được chọn
                selectedToDoId = selectedToDo.getId();
            }
        });


        // Add button event
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDo newToDo = new ToDo(0, titleInput.getText().toString(), contentInput.getText().toString(),
                        dateInput.getText().toString(), typeInput.getText().toString(), 0);
                if (todoDAO.addToDo(newToDo)) {
                    updateToDoList();
                }
            }
        });

        // Update button event
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement logic to get the selected ToDo ID
                int selectedId = getSelectedToDoId();
                ToDo updatedToDo = new ToDo(selectedId, titleInput.getText().toString(), contentInput.getText().toString(),
                        dateInput.getText().toString(), typeInput.getText().toString(), 0);
                if (todoDAO.updateToDo(updatedToDo)) {
                    updateToDoList();
                }
            }
        });

        // Delete button event
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = getSelectedToDoId(); // Implement logic to get selected ToDo ID
                if (todoDAO.deleteToDo(selectedId)) {
                    updateToDoList();
                }
            }
        });
    }

    private void updateToDoList() {
        list.clear();
        list.addAll(todoDAO.getListToDo());
        toDoAdapter.notifyDataSetChanged();
    }

    private int getSelectedToDoId() {
        return selectedToDoId;
    }
}
