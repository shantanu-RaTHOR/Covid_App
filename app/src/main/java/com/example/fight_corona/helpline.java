package com.example.fight_corona;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appolica.flubber.Flubber;

public class helpline extends Fragment
{
    CardView cardView1,toll,up;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.helpline,container,false);



        modelp[] items=new modelp[2];
      items[0]=new modelp("UTTAR PRADESH","9149367771");
      items[1]=new modelp("UTTRAKHAND","9997304104");
        Myaddapter adapter = new Myaddapter(items);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);



        return v;
    }
}
