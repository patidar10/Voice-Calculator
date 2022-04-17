package com.example.skisan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class bhavadapter extends RecyclerView.Adapter<bhavadapter.vholder>{
    ArrayList<bhavdata> alldata;
    Context context;

    public bhavadapter(ArrayList<bhavdata> alldata, Context context) {
        this.alldata = alldata;
        this.context = context;
    }

    @NonNull
    @Override
    public vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.mandibhavlayout,parent,false);

        return new vholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull vholder holder, int position) {
        bhavdata currdata=alldata.get(position);
        holder.place.setText(currdata.getMandiplace());
        Glide.with(context).load(currdata.getBhavphotourl()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return alldata.size();
    }

    public class vholder extends RecyclerView.ViewHolder{
        TextView place;
        ImageView image;

        public vholder(@NonNull View itemView) {
            super(itemView);
            place=itemView.findViewById(R.id.textView);
            image=itemView.findViewById(R.id.imageView);

        }
    }
}
