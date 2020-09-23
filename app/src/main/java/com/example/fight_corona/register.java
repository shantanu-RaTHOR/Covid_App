package com.example.fight_corona;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnSuccessListener;
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

public class register extends AppCompatActivity
{


    EditText em,an,pw,dte;
    RequestQueue requestQueue;
    Button regis;
    FirebaseAuth firebaseAuth;
    String email,aadhar,password,date;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        em=(EditText) findViewById(R.id.email);
        an=(EditText) findViewById(R.id.adhaar);
        pw=(EditText) findViewById(R.id.password);
        dte=(EditText) findViewById(R.id.date);
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=(ProgressBar) findViewById(R.id.pbregister);

    }
    public void register1(View v)
    {
        email=em.getText().toString().trim();
        aadhar=an.getText().toString().trim();
        password=pw.getText().toString().trim();
        date=dte.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            em.setError("Enter a valid mail");
            em.requestFocus();
            return;
        }

        else if (password.length() < 6)
        {
           pw.setError("Passwoed should be 6 chars");
            pw.requestFocus();
            return;
        }
        else if(aadhar.equals("")||password.equals("")||date.equals(""))
        {
            Toast.makeText(getApplicationContext(),"ENTER ALL DETAILS",Toast.LENGTH_LONG).show();
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"user registered succesfully",Toast.LENGTH_LONG).show();
                           // FirebaseUser user= firebaseAuth.getCurrentUser();
                            //Toast.makeText(getApplicationContext(),user.getUid(),Toast.LENGTH_LONG).show();
                            Intent i=new Intent(getApplicationContext(),login.class);
                            startActivity(i);
                            progressBar.setVisibility(View.GONE);


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), task.getException().toString(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                }
            });






        }

    }
    public void signin(View v)
    {
        Intent i=new Intent(this,login.class);
        startActivity(i);
    }

}

