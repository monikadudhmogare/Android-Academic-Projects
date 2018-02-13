package com.example.monika.smartstreetassign;

import java.io.File;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Environment;
import android.net.Uri;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
     * Created by monika on 9/19/17.
     */

    public class ShareActivity extends AppCompatActivity
    {
        static String TAG = "ShareActivity";
        Button vdoButton, buttonShareImage;
        private Button shareAudioButton;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_share);

           OnClickListener handler = new OnClickListener() {
                public void onClick(View v) {
                    switch (v.getId())
                    {

                        case R.id.vdoButton:
                            shareMediaVdo();
                            break;

                        case R.id.buttonShareImage:
                            shareImage();
                            break;

                    }
                }
            };

            findViewById(R.id.vdoButton).setOnClickListener(handler);
            findViewById(R.id.buttonShareImage).setOnClickListener(handler);

            shareAudioButton = (Button) findViewById(R.id.shareRecord);
            vdoButton = (Button) this.findViewById(R.id.vdoButton);

            buttonShareImage = (Button) this.findViewById(R.id.buttonShareImage);
            buttonShareImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    shareImage();

                }
            });

            shareAudioButton = (Button) findViewById(R.id.shareRecord);
            shareAudioButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    {
                        Log.i(TAG, "Audio");
                        Intent audioIntent = new Intent(ShareActivity.this, AudioRecordActivity.class);
                        startActivity(audioIntent);

                    }
                }
            });

        }

        // Method to share video.
        private void shareMediaVdo()
        {
            Intent vshare = new Intent(android.content.Intent.ACTION_SEND);
            vshare.setType("video/*");


            String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                   + "/myVideo.png";


            File videoFileToShare = new File(videoPath);

           Uri uri = Uri.fromFile(videoFileToShare);
            vshare.putExtra(Intent.EXTRA_STREAM, uri);

           startActivity(Intent.createChooser(vshare, "Share Video!"));

        }

        // Method to share image.

        private void shareImage()
            {
            Intent share = new Intent(Intent.ACTION_SEND);

            share.setType("image/*");


            String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/myImage.png";

            //outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    //"/record_audio_sample" + ".mp3";


            File imageFileToShare = new File(imagePath);

            Uri uri = Uri.fromFile(imageFileToShare);
            share.putExtra(Intent.EXTRA_STREAM, uri);

            startActivity(Intent.createChooser(share, "Share Image!"));
        }



    }
