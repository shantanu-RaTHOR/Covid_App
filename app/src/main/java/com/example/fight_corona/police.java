package com.example.fight_corona;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class police extends AppCompatActivity

{


    EditText uid;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);
        init();



    }
    public void init()
    {
        uid=(EditText) findViewById(R.id.uidp);

    }
    public void  loginP(View v)
    {
        init();
        String v2=uid.getText().toString().trim();
            if(v2.equals("1200"))
        {
            Intent i = new Intent(getApplicationContext(), scanner.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "WRONG UID", Toast.LENGTH_LONG).show();
        }

    }

    public void  frst(View v)
    {
        Intent i=new Intent(getApplicationContext(),login.class);
        startActivity(i);

    }


}
