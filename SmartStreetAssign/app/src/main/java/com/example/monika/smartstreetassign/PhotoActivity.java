package com.example.monika.smartstreetassign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by monika on 9/18/17.
 */

public class PhotoActivity extends AppCompatActivity {

    private static final int CAM_REQ=1888;
    ImageView imgView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);


        imgView = (ImageView) this.findViewById(R.id.image);
        button = (Button) this.findViewById(R.id.picButton);

    }

    /* Handling of the Image Button when clicked */

    public void imageClick(View view)
    {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAM_REQ);
    }

    protected void onActivityResult(int reqCode, int outputCode, Intent data)
    {
        if(reqCode == CAM_REQ && outputCode == RESULT_OK)
        {
            Bitmap pic = (Bitmap) data.getExtras().get("data");
            imgView.setImageBitmap(pic);
        }
    }
}
