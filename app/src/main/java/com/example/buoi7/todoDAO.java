package com.example.buoi7;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class todoDAO {

    private final DbHelper dbHelper;


    public todoDAO(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    // Method to retrieve a list of ToDo items from the database
    public ArrayList<ToDo> getListToDo() {
        ArrayList<ToDo> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();


        database.beginTransaction();
        try {
            // Query the TODO table for all entries
            Cursor cursor = database.rawQuery("SELECT * FROM TODO", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    // Add a new ToDo object to the list for each row in the result set
                    list.add(new ToDo(cursor.getInt(0), // ID
                            cursor.getString(1), // Title
                            cursor.getString(2), // Content
                            cursor.getString(3), // Date
                            cursor.getString(4), // Type
                            cursor.getInt(5))); // Status
                } while (cursor.moveToNext());
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {

            Log.e(TAG, "get listtodo", e);
        } finally {

            database.endTransaction();
        }

        return list;
    }
    public boolean addToDo(ToDo todo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", todo.getTitle());
        values.put("CONTENT", todo.getContent());
        values.put("DATE", todo.getDate());
        values.put("TYPE", todo.getType());
        values.put("STATUS", todo.getStatus());

        long result = db.insert("TODO", null, values);
        return result != -1;
    }
    public boolean updateToDo(ToDo todo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", todo.getTitle());
        values.put("CONTENT", todo.getContent());
        values.put("DATE", todo.getDate());
        values.put("TYPE", todo.getType());
        values.put("STATUS", todo.getStatus());

        int result = db.update("TODO", values, "ID=?", new String[] { String.valueOf(todo.getId()) });
        return result > 0;
    }
    public boolean deleteToDo(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("TODO", "ID=?", new String[] { String.valueOf(id) });
        return result > 0;
    }





}
