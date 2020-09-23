package com.example.fight_corona;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appolica.flubber.Flubber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class statedata extends Fragment {
    RequestQueue requestQueue;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     final    View v = inflater.inflate(R.layout.activity_statedata, container, false);

        {
            String url = "https://api.covid19india.org/data.json";

            Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
            recyclerView=(RecyclerView) v.findViewById(R.id.staterv);

// Set up the network to use HttpURLConnection as the HTTP client.
            Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("statewise");
                        int i = 0;
                        modelstate[] items = new modelstate[jsonArray.length()];
                        for (i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String active = jsonObject.getString("active");
                            String confirmed = jsonObject.getString("confirmed");
                            String death = jsonObject.getString("deaths");
                            String state = jsonObject.getString("state");
                            items[i] = new modelstate(active, confirmed, death, state);
                        }


                        addapterstate adapter = new addapterstate(items);

                        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.staterv);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);


                        // Toast.makeText(getApplicationContext(),jsonArray.toString(),Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "ERROR in recyle", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "ERROR inresponse", Toast.LENGTH_LONG).show();

                }
            });
            requestQueue.add(jsonObjectRequest);
            //modelstate[] items=new modelstate[2];

            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_IN_UP) // Slide up animation
                    .repeatCount(0)                              // Repeat once
                    .duration(1500)                              // Last for 1000 milliseconds(1 second)
                    .createFor(recyclerView)                             // Apply it to the view
                    .start();


        }

        return v;
    }
}