package com.example.fight_corona;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Myaddapter extends RecyclerView.Adapter<Myaddapter.ViewHolder>
{


    public modelp  item[];
    public Myaddapter(modelp[] items)
    {
        item=items;
    }

    @NonNull
    @Override
    public Myaddapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.listitemph, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Myaddapter.ViewHolder holder, int i)
    {

        final  modelp myListData =item[i];
        holder.state.setText(myListData.getState());
        holder.phone.setText(myListData.getPhone());

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView state,phone;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            this.state = (TextView) itemView.findViewById(R.id.state);
            this.phone=(TextView) itemView.findViewById(R.id.phone);
        }
    }
}

