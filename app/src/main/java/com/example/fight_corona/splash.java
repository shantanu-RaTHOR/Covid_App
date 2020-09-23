package com.example.fight_corona;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appolica.flubber.Flubber;

public class splash extends AppCompatActivity
{
    TextView textView;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       textView= findViewById(R.id.tv);
       linearLayout=findViewById(R.id.ll);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                Intent i = new Intent(splash.this, login.class);
                startActivity(i);
                finish();
            }
        }, 1000);

    }

}
