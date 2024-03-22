package com.example.todolistapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View dialogView;

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<Task> taskArrayList;
    private DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        dbHelper = new DbHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.rview);
        adapter = new TaskAdapter(this);

        dbHelper = new DbHelper(this);
        taskArrayList = dbHelper.getAllUser();
        adapter.setListVoter(taskArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add){
            // Membuat AlertDialog.Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // Mengatur layout XML pop-up ke dalam AlertDialog.Builder
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.pop_up, null);
            builder.setView(dialogView);

            Button btnBatal = dialogView.findViewById(R.id.btn_batal);

            // Membuat dan menampilkan dialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            btnBatal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Tutup dialog
                    alertDialog.dismiss();

                    // Kembali ke halaman utama
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Opsional, jika Anda ingin menutup aktivitas saat ini
                }
            });

            Button btnSave = dialogView.findViewById(R.id.btn_save);
            EditText edtTask = dialogView.findViewById(R.id.edt_add);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtTask.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "Task tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        dbHelper.addTask(edtTask.getText().toString());
//

                        Toast.makeText(MainActivity.this, "Task Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();

                        Intent saveIntent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(saveIntent);
                        finish();
                    }

                }
            });

        }
//
    }
}