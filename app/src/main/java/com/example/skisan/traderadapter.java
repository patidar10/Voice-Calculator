package com.example.skisan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class traderadapter extends RecyclerView.Adapter<traderadapter.viewholder> {
    ArrayList<userdata> traderslist;
    Context context;

    public traderadapter(ArrayList<userdata> traderslist, Context context) {
        this.traderslist = traderslist;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.traderslayout,parent,false);

        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        userdata currtrader=traderslist.get(position);
        holder.name.setText(currtrader.name);
        holder.number.setText(currtrader.number);
        holder.type.setText(currtrader.tradertype);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return traderslist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView name;
        TextView number;
        TextView type;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView6);
            number=itemView.findViewById(R.id.textView7);
            type=itemView.findViewById(R.id.textView8);
        }
    }
}
