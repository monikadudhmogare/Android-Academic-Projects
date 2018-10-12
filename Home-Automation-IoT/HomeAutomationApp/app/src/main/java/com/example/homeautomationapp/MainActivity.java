package com.example.homeautomationapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Switch led1 = (Switch) findViewById(R.id.Led1);
        Switch led2 = (Switch) findViewById(R.id.Led2);
        Switch led3 = (Switch) findViewById(R.id.Led3);
        Switch led4 = (Switch) findViewById(R.id.Led4);
        ImageButton btn = (ImageButton)findViewById(R.id.btnCancel);



         /*  Setting onchange listener for every switch  */


        led1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    new Background_get().execute("led1=2");
                } else {
                    new Background_get().execute("led1=4");
                }
            }
        });

        led2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {

                    new Background_get().execute("led2=1");
                } else {
                    new Background_get().execute("led2=0");
                }
            }
        });


        led3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {

                    new Background_get().execute("led3=2");
                } else {
                    new Background_get().execute("led3=4");
                }
            }
        });

        led4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {

                    new Background_get().execute("led4=2");
                } else {
                    new Background_get().execute("led4=4");
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


       /*  This is a background process for connecting
           to the raspberry pi server and sending
           the GET request withe the added data           */


    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {

                URL url = new URL("http://10.15.141.217?" + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                Log.v("HTTP: ", String.valueOf(connection.getResponseCode()));

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");

                in.close();
                connection.disconnect();
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

