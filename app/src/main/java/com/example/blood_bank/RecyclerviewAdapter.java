package com.example.blood_bank;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder> implements View.OnClickListener {
    private List<List_data> list;

    private Context context;
    ImageView phone, message;

    public RecyclerviewAdapter(Context context, List<List_data> list) {
        this.list = list;
        this.context = context;

    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_home_page, parent, false);


        return new MyHolder(view);

    }
    String s;
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        List_data data = list.get(position);
        holder.vblood.setText(data.get_BloodGroup());
        holder.vname.setText(data.get_name());
        holder.vnumber.setText(data.get_number());
        holder.vlocation.setText(data.get_location());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView vname,vblood,vnumber,vlocation;
        public MyHolder(View itemView) {
            super(itemView);
            vname=itemView.findViewById(R.id.text_name);
            vblood=itemView.findViewById(R.id.blood_group);
            vnumber=itemView.findViewById(R.id.mobile_number);
            vlocation=itemView.findViewById(R.id.location);
            s=vnumber.getText().toString();
            itemView.setClickable(true);
            itemView.setOnClickListener(RecyclerviewAdapter.this);
        }

    }
}
