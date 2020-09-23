package com.example.fight_corona;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity

{
    RequestQueue requestQueue;
    EditText email,password;
    FirebaseAuth mAuth;
    FirebaseAuth firebaseAuth;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    Boolean doubleBackToExitPressedOnce=false;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        firebaseAuth=FirebaseAuth.getInstance();

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        progressBar=(ProgressBar) findViewById(R.id.pblogin);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

// Start the queue
        requestQueue.start();
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(sharedPreferences.getString("uid","")!="")
        {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);

        }



    }

    public void login(View v) {
        String e = email.getText().toString().trim();
        String p = password.getText().toString().trim();

         if(p.equals("")||e.equals(""))
        {
            Toast.makeText(getApplicationContext(),"FILL ALL DETAILS",Toast.LENGTH_LONG).show();
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    {
                        if (task.isSuccessful())
                        {

                           // Toast.makeText(getApplicationContext(),"user registered succesfully",Toast.LENGTH_LONG).show();
                             FirebaseUser user= firebaseAuth.getCurrentUser();
                   sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("uid", user.getUid());
                            editor.putString("skip", "false");
                            editor.commit();

                            //Toast.makeText(getApplicationContext(),user.getUid(),Toast.LENGTH_LONG).show();



                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                            progressBar.setVisibility(View.GONE);

                            finish();

                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), task.getException().toString(),Toast.LENGTH_LONG).show();

                        }
                    }
                }
            });



        }
    }
    public void skip(View v)
    {

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("skip", "true");
        editor.commit();
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();

    }


    public void signup(View v)
    {
        Intent i=new Intent(login.this,register.class);
        startActivity(i);

    }
    public void loginpp(View v)
    {

        Intent i=new Intent(login.this,police.class);
        startActivity(i);

    }


    public void register(View v)
    {
        Intent i=new Intent(login.this,register.class);
        startActivity(i);


    }


    @Override
    public void onBackPressed()
    {


        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
