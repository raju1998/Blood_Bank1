package com.example.blood_bank;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder> {
    List<List_data> list;
    public RecyclerviewAdapter(List<List_data> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.details_home_page,parent,false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        List_data data=list.get(position);
        holder.vblood.setText(data.get_BloodGroup());
        holder.vname.setText(data.get_name());
        holder.vnumber.setText(data.get_number());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView vname,vblood,vnumber;

        public MyHolder(View itemView) {
            super(itemView);
            vname=itemView.findViewById(R.id.text_name);
            vblood=itemView.findViewById(R.id.blood_group);
            vnumber=itemView.findViewById(R.id.mobile_number);
        }
    }
}
