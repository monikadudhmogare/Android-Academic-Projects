package com.example.monika.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static java.net.URLEncoder.*;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    TextView weatherTextView;

    public void onWeatherClick(View view)
    {
        Log.i("City Name", cityName.getText().toString());

        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        try {
            String encodedCityName = encode(cityName.getText().toString(),"UTF-8");

            DownloadTask task = new DownloadTask();
            task.execute("http://api.openweathermap.org/data/2.5/weather?q=" +encodedCityName +"&appid=be87c88eea6a5ccf2882774254038d42");
        }

        catch (UnsupportedEncodingException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(),"Could not find weather", Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText) findViewById(R.id.cityName);
        weatherTextView = (TextView) findViewById(R.id.weatherTextView);
    }

    public class DownloadTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls)
        {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while(data != -1)
                {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            }

            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Could not find weather", Toast.LENGTH_LONG);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            Log.i("Website Content", result);


            try
            {
                String message ="";

                JSONObject jsonObject = new JSONObject(result);

                String weatherInfo = jsonObject.getString("weather");
                Log.i("Weather content", weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);
                for(int i =0; i<arr.length(); i++)
                {
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main = "";
                    String description = "";

                    main = jsonPart.getString("main");
                    description = jsonPart.getString("description");

                    if(main !="" && description !="")
                    {
                        message += main + ": " + description + "\r\n";
                    }
                }

                if(message !="")
                {
                    weatherTextView.setText(message);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Could not find weather", Toast.LENGTH_LONG);
                }

            }
            catch (JSONException e)
            {
                Toast.makeText(getApplicationContext(),"Could not find weather", Toast.LENGTH_LONG);
            }

        }
    }
}
