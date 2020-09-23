package com.example.fight_corona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class genpass extends Fragment {


    EditText aadar ,slot,date,ar;
    Button btn;
    ImageView iv;
    public final static int QRcodeWidth = 500 ;
    private static final String IMAGE_DIRECTORY = "/QRcodeDemonuts";
    Bitmap bitmap ;
    FirebaseAuth firebaseAuth;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    FirebaseDatabase firebaseDatabase;
    ProgressBar progressBar;
    DatabaseReference databaseReference;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_genpass, container, false);
        aadar=(EditText) v.findViewById(R.id.adhr);
        slot=(EditText) v.findViewById(R.id.tymslt);
        date=(EditText) v.findViewById(R.id.dte);
        ar=(EditText) v.findViewById(R.id.cty);
        btn=(Button) v.findViewById(R.id.btn);
        progressBar=(ProgressBar) v.findViewById(R.id.pbrgen);
        iv = (ImageView) v.findViewById(R.id.iv);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("USER");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genpas();
            }
        });

        FirebaseApp.initializeApp(getActivity());
        firebaseAuth=FirebaseAuth.getInstance();
        sharedPreferences=getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String b=sharedPreferences.getString("skip","");
        if(b.equals("true"))
        {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(getActivity(),"PLEASE LOG IN FIRST",Toast.LENGTH_LONG).show();



                }
            }, 100);
        }




        return  v;

    }

    public  void genpas()
    {
        progressBar.setVisibility(View.VISIBLE);
        String a,slt,dte,cty;
        a=aadar.getText().toString().trim();
        slt=slot.getText().toString().trim();
        dte=date.getText().toString().trim();
        cty=ar.getText().toString().trim();
        if(a.equals("")||slt.equals("")||dte.equals("")||cty.equals(""))
        {
            Toast.makeText(getActivity(),"ENTER ALL DETAILS",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);

        }
        else
        {

            sharedPreferences=getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String b=sharedPreferences.getString("skip","");
            if(b.equals("true"))
            {

                        Toast.makeText(getActivity(),"LOG IN FIRST",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getActivity(),login.class);
                        startActivity(i);
                        getActivity().finish();
                progressBar.setVisibility(View.GONE);

            }
            else {


                //progressBar.setVisibility(View.VISIBLE);
                sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                String userid = sharedPreferences.getString("uid", "");


                try {
                    bitmap = TextToImageEncode(userid);
                    iv.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                DatabaseReference dr = databaseReference.child(userid);
                dr.child("AADHAR NO").setValue(a);
                dr.child("CITY").setValue(cty);
                dr.child("DATE").setValue(dte);
                dr.child("TIME SLOT").setValue(slt);
                progressBar.setVisibility(View.GONE);
            }




            //adddata();


        }





















    }



    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        progressBar.setVisibility(View.VISIBLE);
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }





}
