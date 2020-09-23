package com.example.fight_corona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class report extends Fragment
{
    EditText nm,pl,aa,de;
    Button reportm;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.massgathering, container, false);
        nm=(EditText) v.findViewById(R.id.namem);
        aa=(EditText) v.findViewById(R.id.aadhnom);
        pl=(EditText) v.findViewById(R.id.placem);
        de=(EditText) v.findViewById(R.id.details);
        reportm=(Button) v.findViewById(R.id.report);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String b=sharedPreferences.getString("skip","");
        if(b.equals("true"))
        {
            Toast.makeText(getActivity(),"LOG IN FIRST",Toast.LENGTH_LONG).show();
        }

        reportm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String name,aad,place,details;
                name=nm.getText().toString().trim();
                aad=aa.getText().toString().trim();
                place=pl.getText().toString().trim();
                details=de.getText().toString().trim();
                if(name.equals("")||aad.equals("")||place.equals("")||details.equals(""))
                {
                    Toast.makeText(getActivity(),"ENTER ALL DETAILS",Toast.LENGTH_LONG).show();

                }
                else
                {

                  SharedPreferences sharedPreferences=getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    String b=sharedPreferences.getString("skip","");
                    if(b.equals("true"))
                    {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"LOG IN FIRST",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getActivity(),login.class);
                                startActivity(i);
                                getActivity().finish();


                            }
                        }, 500);
                    }
                    else {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "REPORT SUBMITTED", Toast.LENGTH_LONG).show();


                            }
                        }, 250);



                    }

                }

            }
        });




        return v;
    }
   public  void reportm(View v)
    {


    }
}
