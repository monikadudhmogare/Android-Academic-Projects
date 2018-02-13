package com.example.monika.smartstreetassign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by monika on 9/19/17.
 */

public class InteractActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button interact;
    private TextView aboutme;
    String information;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact);

        interact = (Button) findViewById(R.id.interact);
        aboutme = (TextView) findViewById(R.id.aboutme);

        /* Information to be displayed on button click */

        information = "Smart Streets provides a better way to use the smart technology.\n" + "It is beneficial for everyone specially the Homeowners and the Businessman. \n" +"It helps to use the energy in an efficient way.";
        interact.setOnClickListener(InteractActivity.this);
    }

        public void onClick(View v)
        {
            aboutme.setText(information);
        }
            


}
