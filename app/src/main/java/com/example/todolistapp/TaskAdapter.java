package com.example.todolistapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.VoterViewHolder> {
    private ArrayList<Task> listTask = new ArrayList<>();
    private Activity activity;

    public TaskAdapter(Activity activity){
        this.activity = activity;
        DbHelper dbHelper = new DbHelper(activity);
    }

    public ArrayList<Task> getListTask(){
        return listTask;
    }

    public void setListVoter(ArrayList<Task> listTask){
        if (listTask.size() > 5){
            this.listTask.clear();
        }
        this.listTask.addAll(listTask);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskAdapter.VoterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new VoterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.VoterViewHolder holder, int position) {
        holder.tvTask.setText(listTask.get(position).getTask());

        holder.cvItem.setOnClickListener((View v) -> {
            Intent detailIntent = new Intent(activity, MainActivity.class);
            detailIntent.putExtra("voter", (Serializable) listTask.get(position));
            activity.startActivity(detailIntent);
        } );
//        holder.btnUpdate.setOnClickListener((View v) -> {
//            Intent updateIntent = new Intent(activity, UpdateActivity.class);
//            updateIntent.putExtra("voter", (Serializable) listVoter.get(position));
//            activity.startActivity(updateIntent);
//        });
        holder.btnDelete.setOnClickListener((View v) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle("Konfirmasi Hapus");
            builder.setMessage("Apakah anda yakin menghapus ?");

            builder.setPositiveButton("YA", (dialog, which) -> {
                DbHelper dbHelper = new DbHelper(activity);
                dbHelper.deleteTask(listTask.get(position).getId());
                Toast.makeText(activity, "Hapus Berhasil", Toast.LENGTH_SHORT).show();
                Intent delIntent = new Intent(activity, MainActivity.class);
                activity.startActivity(delIntent);
                activity.finish();
            });

            builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        return listTask.size();
    }

    public class VoterViewHolder extends RecyclerView.ViewHolder {

        TextView tvTask;
        CardView cvItem;

        Button btnDelete, btnUpdate;
        public VoterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tv_item_task);

            cvItem = itemView.findViewById(R.id.cv_item_task);
            btnDelete = itemView.findViewById(R.id.btn_del);
//            btnUpdate = itemView.findViewById(R.id.btn_update);
        }
    }
}

