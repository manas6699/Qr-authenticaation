package com.alfresco.qradmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VerificationDB extends AppCompatActivity {

    private static final String apiurl="https://185.239.209.112:2083/login_maker.php";
    EditText t1,t2;
    TextView tv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_db);
    }


    public void login_process(View view)
    {
        t1= findViewById(R.id.t1);
        t2= findViewById(R.id.t2);
        tv= findViewById(R.id.tv);

        String qry="?t1="+t1.getText().toString().trim()+"&t2="+t2.getText().toString().trim();

        @SuppressLint("StaticFieldLeak")
        class dbprocess extends AsyncTask<String,Void,String>
        {
            @Override
            protected  void onPostExecute(String data)
            {
                if(data.equals("found"))
                {

                    Toast.makeText(VerificationDB.this, "Verified / Allowed", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else
                {
                    t1.setText("");
                    t2.setText("");
//                    tv.setTextColor(Color.parseColor("#8B0000"));
                    tv.setText(data);
                }
            }
            @Override
            protected String doInBackground(String... params)
            {
                String furl=params[0];

                try
                {
                    URL url=new URL(furl);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    return br.readLine();

                }catch (Exception ex)
                {
                    return ex.getMessage();
                }
            }
        }

        dbprocess obj=new dbprocess();
        obj.execute(apiurl+qry);

    }

}
