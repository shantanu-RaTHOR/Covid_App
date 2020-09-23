package com.example.fight_corona;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.content.Context.CONNECTIVITY_SERVICE;

public class Home extends Fragment
{
    TextView active,discharge,death,passenger,basic,stated;
    ProgressDialog progressDialog;

    MaterialCardView card1,card2,card3,card4;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView internet;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.home,container,false);
        card1 = v.findViewById(R.id.card1);
        card2 = v.findViewById(R.id.card2);
        card3 = v.findViewById(R.id.card3);
        card4 = v.findViewById(R.id.card4);
        internet=(TextView) v.findViewById(R.id.internet);
        basic=v.findViewById(R.id.basic);
        stated=(TextView) v.findViewById(R.id.stated);


        active = (TextView) v.findViewById(R.id.active);
        discharge = (TextView) v.findViewById(R.id.discharge);
        death = (TextView) v.findViewById(R.id.death);
        passenger = (TextView) v.findViewById(R.id.passenger);
        swipeRefreshLayout=v.findViewById(R.id.swipe);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setMessage("FETCHING DATA");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        if (!haveNetwork())
        {
            Toast.makeText(getActivity(), "Network connection is not available", Toast.LENGTH_SHORT).show();
            internet.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
        }
        else
        {
            internet.setVisibility(View.GONE);
            if(savedInstanceState==null)
            new data().execute();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                if (!haveNetwork())
                {
                    internet.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Network connection is not available", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new data().execute();
                    internet.setVisibility(View.GONE);
                }


            }
        });

        stated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment1 = new statedata();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment1);
                ft.addToBackStack(null);
                ft.commit();
            }
        });





        return  v;
    }




    private boolean haveNetwork(){

        ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return (mNetworkInfo == null) ? false : true;
    }








    class data extends AsyncTask<Void, ArrayList,ArrayList> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList doInBackground(Void... voids)
        {

            ArrayList<String> arrayList = new ArrayList<>();

            try {
                String url = "https://www.mygov.in/covid-19/";
                Document document = Jsoup.connect(url).get();
                Elements elements = document.getElementsByClass("icount");

                arrayList.add(elements.get(0).text());
                arrayList.add(elements.get(1).text());
                arrayList.add(elements.get(2).text());
                arrayList.add(elements.get(3).text());

            }catch (Exception e)
            {

            }
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {

            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_IN_RIGHT)
                    .repeatCount(0)
                    .duration(1500)
                    .createFor(card1)
                    .start();
            card1.setVisibility(View.VISIBLE);

            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_IN_LEFT) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1500)                              // Last for 1000 milliseconds(1 second)
                    .createFor(card2)                             // Apply it to the view
                    .start();
            card2.setVisibility(View.VISIBLE);

            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_IN_RIGHT) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1500)                              // Last for 1000 milliseconds(1 second)
                    .createFor(card3)                             // Apply it to the view
                    .start();
            card3.setVisibility(View.VISIBLE);

            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_IN_LEFT)
                    .repeatCount(0)
                    .duration(1500)                              // Last for 1000 milliseconds(1 second)
                    .createFor(card4)                             // Apply it to the view
                    .start();
            card4.setVisibility(View.VISIBLE);


            // SET VALUES :
            basic.setVisibility(View.VISIBLE);
            passenger.setText(arrayList.get(3).toString());
            active.setText(arrayList.get(0).toString());
            discharge.setText(arrayList.get(1).toString());
            death.setText(arrayList.get(2).toString());
            swipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(arrayList);
            progressDialog.dismiss();
        }
    }
    }





