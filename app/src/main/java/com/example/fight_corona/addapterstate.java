package com.example.fight_corona;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class addapterstate extends RecyclerView.Adapter<addapterstate.ViewHolder>
{

    public modelstate item[];
    int no=0;
    public addapterstate(modelstate[] items)
    {
        item=items;
        no=item.length;
    }

    @NonNull
    @Override
    public addapterstate.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.staterow, viewGroup, false);
        //Myaddapter.ViewHolder viewHolder = new Myaddapter.ViewHolder(listItem);
        addapterstate.ViewHolder viewHolder=new addapterstate.ViewHolder(listItem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull addapterstate.ViewHolder viewHolder, int i)
    {

        final  modelstate myListData =item[i];
        viewHolder.confirmed.setText(myListData.getConfirmed());
        viewHolder.death.setText(myListData.getDeaths());
        viewHolder.active.setText(myListData.getActive());
        viewHolder.state.setText(myListData.getState());


    }

    @Override
    public int getItemCount()
    {
        return (no);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView active,death,confirmed,state;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            this.active = (TextView) itemView.findViewById(R.id.active);
            this.death=(TextView) itemView.findViewById(R.id.dead);
            this.confirmed=(TextView)itemView.findViewById(R.id.confirmed);
            this.state=(TextView)itemView.findViewById(R.id.state);

        }
    }
}
