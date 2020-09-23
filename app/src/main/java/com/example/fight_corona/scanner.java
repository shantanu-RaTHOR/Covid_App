package com.example.fight_corona;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class scanner extends AppCompatActivity
{

    private IntentIntegrator qrScan;
    Button btn;
    TextView an,city,dateE,tymsltE,valid;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        init();
        databaseReference= FirebaseDatabase.getInstance().getReference("USER");
        qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();


    }
    void init()
    {
        an=(TextView) findViewById(R.id.adharno);
        city=(TextView) findViewById(R.id.cityy);
        dateE=(TextView) findViewById(R.id.datee);
        tymsltE=(TextView) findViewById(R.id.slttE);
        valid=(TextView) findViewById(R.id.valid);

    }


    public void scann(View v)
    {
        init();
        qrScan.initiateScan();


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            if (result.getContents() == null)
            {
                Toast.makeText(this, "NO QR CODE SCANNED", Toast.LENGTH_LONG).show();
                valid.setText("INVALID");
                an.setText("--");
                city.setText("--");
                dateE.setText("--");
                tymsltE.setText("--");

            }
            else
                {
                try
                {
                    String uid=result.getContents();
                    databaseReference=databaseReference.child(uid);


                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                          String aaa=  dataSnapshot.child("AADHAR NO").getValue(String.class);
                          String c=dataSnapshot.child("CITY").getValue(String.class);
                          if(aaa==null)
                          {
                              Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_LONG).show();
                              valid.setText("INVALID");
                              an.setText("--");
                              city.setText("--");
                              dateE.setText("--");
                              tymsltE.setText("--");

                          }
                          else
                          {
                              Toast.makeText(getApplicationContext(), "DATA FOUND", Toast.LENGTH_LONG).show();
                              an.setText("-"+aaa);
                              city.setText("-"+c);
                              dateE.setText("-"+dataSnapshot.child("DATE").getValue(String.class));
                              tymsltE.setText("-"+dataSnapshot.child("TIME SLOT").getValue(String.class));
                              valid.setText("VALID");



                          }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {
                            Toast.makeText(getApplicationContext(), "NO DATA FOUND error", Toast.LENGTH_LONG).show();

                        }
                    });















                } catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_LONG).show();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                   // Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        }
        else
            {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                an.setText("--");
                city.setText("--");
                dateE.setText("--");
                tymsltE.setText("--");
                valid.setText("INVALID");
          }
    }

    protected void onStart()
    {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                 {


                                                 }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                 }
                                             }
        );
    }
}
