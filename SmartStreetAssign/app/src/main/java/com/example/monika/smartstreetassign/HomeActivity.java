package com.example.monika.smartstreetassign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by monika on 10/24/17.
 */

public class HomeActivity extends AppCompatActivity {

    static String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        barcodeClick();
        interactClick();
        camClick();
        shareClick();
        nearbyClick();
        commentsClick();
    }

    /* Handling of the Camera Button on HomeScreen */

    private void camClick()
    {
        Button camButton = (Button) findViewById(R.id.cameraButton);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Camera operations to be performed");
                Intent intent = new Intent(HomeActivity.this, PhotoActivity.class);
                startActivity(intent);
            }
        });
    }

   /* Handling of the Share Button on HomeScreen */

    private void shareClick()
    {
        Button shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Share operations to be performed");
                Intent intent = new Intent(HomeActivity.this, ShareActivity.class);
                startActivity(intent);
            }
        });
    }

    /* Handling of the Interact Button on HomeScreen */

    private void interactClick()
    {
        Button interactButton = (Button) findViewById(R.id.interactButton);
        interactButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.i(TAG, "Interaction");
                Intent intent = new Intent(HomeActivity.this, InteractActivity.class);
                startActivity(intent);
            }
        });
    }

    /* Handling of the Location Button on HomeScreen */

    private void nearbyClick()
    {
        Button nearby = (Button) findViewById(R.id.nearbyButton);
        nearby.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.i(TAG, "Locations");
                Intent intent = new Intent(HomeActivity.this, NearByLocation.class);
                startActivity(intent);
            }
        });
    }

    /* Handling of the Barcode Button on HomeScreen */

    private void barcodeClick()
    {
        Button barcode = (Button) findViewById(R.id.barcodeButton);
        barcode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.i(TAG, "Barcode");
                Intent intent = new Intent(HomeActivity.this, BarcodeActivity.class);
                startActivity(intent);
            }
        });
    }

    /* Handling of the Comments Button on HomeScreen */

    private void commentsClick()
    {
        Button comments1 = (Button) findViewById(R.id.commentsButton);
        comments1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.i(TAG, "Comments");
                Intent intent = new Intent(HomeActivity.this, CommentsActivity.class);
                startActivity(intent);
            }
        });
    }
}
