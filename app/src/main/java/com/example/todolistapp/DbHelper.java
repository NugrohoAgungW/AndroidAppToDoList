package com.example.todolistapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.example.todolistapp.Task;

public class DbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "db_todolist";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLES_STD = "todolist";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK = "task";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TABLE_TASK = "CREATE TABLE "
            + TABLES_STD + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TASK + " TEXT );";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addTask(String task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK, task);

        db.insert(TABLES_STD, null, values);
    }

    @SuppressLint("Range")
    public ArrayList<Task> getAllUser(){
        ArrayList<Task> taskModel = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLES_STD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                Task tugas = new Task();
                tugas.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                tugas.setTask(c.getString(c.getColumnIndex(KEY_TASK)));
                taskModel.add(tugas);
            } while (c.moveToNext());
        }
        return taskModel;
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLES_STD, KEY_ID + " = ?", new String[] {String.valueOf(id)});
    }
}
